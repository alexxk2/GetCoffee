package com.example.getcoffee.domain.models

data class Location (
    val id: Int,
    val name: String,
    val point: Point
)

data class Point(
    val latitude: Double,
    val longitude: Double
)

