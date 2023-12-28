package com.example.getcoffee.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    /*@Binds
    @Singleton
    abstract fun bindCoffeeShopsRepository(coffeeShopsRepositoryImpl: CoffeeShopsRepositoryImpl): CoffeeShopsRepository*/
}