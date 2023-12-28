package com.example.getcoffee.data.network

import com.example.getcoffee.data.network.dto.AuthRequestBody
import com.example.getcoffee.data.network.dto.AuthResponse
import com.example.getcoffee.data.network.dto.LocationDto
import com.example.getcoffee.data.network.dto.MenuItemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeShopsApi {

    @POST("/auth/register")
    suspend fun singUp(@Body request: AuthRequestBody): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun signIn(@Body request: AuthRequestBody): Response<AuthResponse>

    @GET("/locations")
    suspend fun getLocations(@Header("Authorization") token: String): Response<List<LocationDto>>

    @GET("/location/{id}/menu")
    suspend fun getMenu(@Path("id") id: Int, @Header("Authorization") token: String): Response<List<MenuItemDto>>

}