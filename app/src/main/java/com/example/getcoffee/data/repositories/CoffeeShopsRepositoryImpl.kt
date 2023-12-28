package com.example.getcoffee.data.repositories

import com.example.getcoffee.data.converters.NetworkConverter
import com.example.getcoffee.data.network.NetworkClient
import com.example.getcoffee.domain.models.Location
import com.example.getcoffee.domain.models.MenuItem
import com.example.getcoffee.domain.repositories.CoffeeShopsRepository
import javax.inject.Inject

class CoffeeShopsRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: NetworkConverter
) : CoffeeShopsRepository {


    override suspend fun signUp(login: String, password: String): String =
        networkClient.signUp(login, password)


    override suspend fun signIn(login: String, password: String): String =
        networkClient.signIn(login, password)

    override suspend fun getLocations(token: String): List<Location> {
        val result = networkClient.getLocations(token)
        return result.map {
            converter.convertLocationToDomain(it)
        }
    }

    override suspend fun getMenu(locationId: Int, token: String): List<MenuItem> {
        val result = networkClient.getMenu(locationId, token)
        return result.map {
            converter.convertMenuItemToDomain(it)
        }
    }
}