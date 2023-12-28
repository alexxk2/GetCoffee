package com.example.getcoffee.presentation.coffeeshops.models

import com.example.getcoffee.domain.models.MenuItem

sealed interface MenuState{
    data class Content(val menus: List<MenuItem>):MenuState
    object Error:MenuState
    object Loading:MenuState
    data class OrderContent(val menus: List<MenuItem>):MenuState
    object EmptyOrder: MenuState
}