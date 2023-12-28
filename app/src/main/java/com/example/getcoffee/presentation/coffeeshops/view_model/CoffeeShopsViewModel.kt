package com.example.getcoffee.presentation.coffeeshops.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getcoffee.domain.coffeeshops.GetLocationsUseCase
import com.example.getcoffee.presentation.coffeeshops.models.CoffeeShopsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeShopsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    /*private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations*/

    private val _state = MutableLiveData<CoffeeShopsState>()
    val state: LiveData<CoffeeShopsState> = _state

    fun getLocations(token: String) {
        _state.postValue(CoffeeShopsState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getLocationsUseCase.execute(token)
                if (response.isNotEmpty()) {
                    _state.postValue(CoffeeShopsState.Content(response))
                } else {
                    _state.postValue(CoffeeShopsState.Error)
                }
            } catch (e: Exception) {
                Log.d("MY_TAG", e.message ?: "")
                _state.postValue(CoffeeShopsState.Error)
            }
        }
    }

}