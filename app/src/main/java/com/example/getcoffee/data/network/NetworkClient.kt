package com.example.getcoffee.data.network

import com.example.getcoffee.data.network.dto.LocationDto
import com.example.getcoffee.data.network.dto.MenuItemDto

interface NetworkClient {
    suspend fun signUp(login: String, password: String): String
    suspend fun signIn(login: String, password: String): String
    suspend fun getLocations(token: String): List<LocationDto>
    suspend fun getMenu(id: Int, token: String): List<MenuItemDto>
}