package com.example.getcoffee.domain.registration

import com.example.getcoffee.domain.repositories.CoffeeShopsRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val coffeeShopsRepository: CoffeeShopsRepository) {
    suspend fun execute(login: String, password: String): String = coffeeShopsRepository.signUp(login, password)
}