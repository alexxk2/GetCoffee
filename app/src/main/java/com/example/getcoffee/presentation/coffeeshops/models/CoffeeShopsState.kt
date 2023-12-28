package com.example.getcoffee.presentation.coffeeshops.models

import com.example.getcoffee.domain.models.Location

sealed interface CoffeeShopsState{
    data class Content(val locations: List<Location>):CoffeeShopsState
    object Error:CoffeeShopsState
    object Loading:CoffeeShopsState
}