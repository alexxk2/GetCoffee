package com.example.getcoffee.data.network.impl

import com.example.getcoffee.data.network.CoffeeShopsApi
import com.example.getcoffee.data.network.NetworkClient
import com.example.getcoffee.data.network.dto.AuthRequestBody
import com.example.getcoffee.data.network.dto.LocationDto
import com.example.getcoffee.data.network.dto.MenuItemDto
import javax.inject.Inject

class NetworkClientImpl @Inject constructor(private val retrofitService: CoffeeShopsApi) :
    NetworkClient {

    override suspend fun signUp(login: String, password: String): String {
        val response = retrofitService.singUp(
            request = AuthRequestBody(login, password)
        )
        return if (response.isSuccessful) {
            response.body()?.token ?: ""
        } else ""
    }

    override suspend fun signIn(login: String, password: String): String {
        val response = retrofitService.signIn(
            request = AuthRequestBody(login, password)
        )
        return if (response.isSuccessful) {
            response.body()?.token ?: ""
        } else ""
    }

    override suspend fun getLocations(token: String): List<LocationDto> {
        val response = retrofitService.getLocations(token)
        return if (response.isSuccessful) {
            response.body().orEmpty()
        } else {
            emptyList()
        }
    }

    override suspend fun getMenu(id: Int, token: String): List<MenuItemDto> {
        val response = retrofitService.getMenu(id,token)
        return if (response.isSuccessful) {
            response.body().orEmpty()
        } else emptyList()
    }
}