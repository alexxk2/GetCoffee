package com.example.getcoffee.presentation.authorization.models

sealed interface AuthState {
    data class Success(val token: String) : AuthState
    object NotSuccess : AuthState
    object Default : AuthState
}