package com.example.getcoffee.domain.models

data class MenuItem(
    val id: Int,
    val name: String,
    val imageURL : String,
    val price: Int,
    var count: Int = 0
)
