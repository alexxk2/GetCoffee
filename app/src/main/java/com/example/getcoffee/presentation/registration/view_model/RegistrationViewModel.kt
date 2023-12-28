package com.example.getcoffee.presentation.registration.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getcoffee.domain.registration.RegistrationUseCase
import com.example.getcoffee.presentation.authorization.models.AuthState
import com.example.getcoffee.presentation.authorization.view_model.AuthorizationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _state = MutableLiveData<AuthState>(AuthState.Default)
    val state: LiveData<AuthState> = _state


    fun signUp(login: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {
           try {
               val response = registrationUseCase.execute(login, password)
               if (response.isNotBlank()){
                   _state.postValue(AuthState.Success(response))
               }
               else _state.postValue(AuthState.NotSuccess)
               delay(AuthorizationViewModel.BACK_TO_DEFAULT_STATE_DELAY)
               _state.postValue(AuthState.Default)
           }
           catch (e: Exception) {
               Log.d("MY_TAG", e.message ?: "")
           }

        }
    }
}