package com.example.getcoffee.presentation.menu.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getcoffee.domain.menu.GetMenuUseCase
import com.example.getcoffee.domain.models.MenuItem
import com.example.getcoffee.presentation.coffeeshops.models.MenuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase
): ViewModel() {

    private var order = mutableListOf<MenuItem>()
    private lateinit var menus: MutableList<MenuItem>

    private val _state = MutableLiveData<MenuState>()
    val state: LiveData<MenuState> = _state

    fun getLocations(id: Int, token: String) {
        _state.postValue(MenuState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getMenuUseCase.execute(id, token)
                if (response.isNotEmpty()) {
                    _state.postValue(MenuState.Content(response))
                    menus = response.toMutableList()
                } else {
                    _state.postValue(MenuState.Error)
                }
            } catch (e: Exception) {
                Log.d("MY_TAG", e.message ?: "")
                _state.postValue(MenuState.Error)
            }
        }
    }

    fun addPosition(item: MenuItem, count: Int) {
        val position = menus.indexOfFirst { it.id == item.id }
        menus[position].count++

        if (order.map { it.id }.contains(item.id)) {
            val orderPosition = order.indexOfFirst { it.id == item.id }
            order[orderPosition].count++
        } else {
            order.add(item.copy(count = count))
        }


    }

    fun removePosition(item: MenuItem) {
        val position = menus.indexOfFirst { it.id == item.id }
        if (menus[position].count>0) menus[position].count--

        if (order.map { it.id }.contains(item.id)) {
            val orderPosition = order.indexOfFirst { it.id == item.id }
            order[orderPosition].count--
            if (order[orderPosition].count == 0) {
                order.removeAt(orderPosition)
                if (state.isInitialized && state.value is MenuState.OrderContent) {
                    stateOrder()
                }
            }
        } else return

    }

    fun stateContent(){
        val tempList = ArrayList<MenuItem>()
        tempList.addAll(menus)
        _state.postValue(MenuState.Content(tempList))
    }

    fun stateOrder(){
        if (order.isEmpty()){
            _state.postValue(MenuState.EmptyOrder)
            return
        }
        val tempList = ArrayList<MenuItem>()
        tempList.addAll(order)
        _state.postValue(MenuState.OrderContent(tempList))
    }
}