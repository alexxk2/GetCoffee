package com.example.getcoffee.domain.menu

import com.example.getcoffee.domain.models.MenuItem
import com.example.getcoffee.domain.repositories.CoffeeShopsRepository
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(private val coffeeShopsRepository: CoffeeShopsRepository) {
    suspend fun execute(locationId: Int, token: String): List<MenuItem> = coffeeShopsRepository.getMenu(locationId,token)
}