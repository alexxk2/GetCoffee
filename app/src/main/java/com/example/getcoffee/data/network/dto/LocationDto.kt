package com.example.getcoffee.data.network.dto

data class LocationDto (
    val id: Int,
    val name: String,
    val point: PointDto
)

data class PointDto(
    val latitude: Double,
    val longitude: Double
)


