package com.example.getcoffee.di


import com.example.getcoffee.data.converters.NetworkConverter
import com.example.getcoffee.data.network.CoffeeShopsApi
import com.example.getcoffee.data.network.NetworkClient
import com.example.getcoffee.data.network.impl.NetworkClientImpl
import com.example.getcoffee.data.repositories.CoffeeShopsRepositoryImpl
import com.example.getcoffee.domain.repositories.CoffeeShopsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val baseUrl = "http://147.78.66.203:3210"
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideCoffeeShopApi(retrofit: Retrofit): CoffeeShopsApi{
        return retrofit.create(CoffeeShopsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(retrofitService: CoffeeShopsApi): NetworkClient{
        return NetworkClientImpl(retrofitService)
    }

    @Provides
    @Singleton
    fun provideCoffeeShopRepository(networkClient: NetworkClient,networkConverter: NetworkConverter): CoffeeShopsRepository{
        return CoffeeShopsRepositoryImpl(networkClient,networkConverter)
    }
}