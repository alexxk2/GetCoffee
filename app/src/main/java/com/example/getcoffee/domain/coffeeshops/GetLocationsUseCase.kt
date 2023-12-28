package com.example.getcoffee.domain.coffeeshops

import com.example.getcoffee.domain.models.Location
import com.example.getcoffee.domain.repositories.CoffeeShopsRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val coffeeShopsRepository: CoffeeShopsRepository) {
    suspend fun execute(token: String): List<Location> = coffeeShopsRepository.getLocations(token)
}