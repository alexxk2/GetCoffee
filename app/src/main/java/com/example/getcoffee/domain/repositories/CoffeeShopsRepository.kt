package com.example.getcoffee.domain.repositories

import com.example.getcoffee.domain.models.Location
import com.example.getcoffee.domain.models.MenuItem

interface CoffeeShopsRepository {
    suspend fun signUp(login: String, password: String): String
    suspend fun signIn(login: String, password: String): String
    suspend fun getLocations(token: String): List<Location>
    suspend fun getMenu(locationId: Int, token: String): List<MenuItem>
}