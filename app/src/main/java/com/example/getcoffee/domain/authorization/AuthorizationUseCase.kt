package com.example.getcoffee.domain.authorization

import com.example.getcoffee.domain.repositories.CoffeeShopsRepository
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(private val coffeeShopsRepository: CoffeeShopsRepository) {
    suspend fun execute(login: String, password: String): String = coffeeShopsRepository.signIn(login, password)
}