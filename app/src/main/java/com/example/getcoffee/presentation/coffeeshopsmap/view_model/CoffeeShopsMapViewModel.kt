package com.example.getcoffee.presentation.coffeeshopsmap.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getcoffee.domain.coffeeshops.GetLocationsUseCase
import com.example.getcoffee.domain.models.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeShopsMapViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) :
    ViewModel() {


    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    fun getLocations(token: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getLocationsUseCase.execute(token)
                if (response.isNotEmpty()) {
                    _locations.postValue(response)
                } else {
                    _locations.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.d("MY_TAG", e.message ?: "")
                _locations.postValue(emptyList())
            }
        }
    }
}