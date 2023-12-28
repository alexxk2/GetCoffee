package com.example.getcoffee.app

import android.app.Application
import com.example.getcoffee.BuildConfig
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val mapKitApi = BuildConfig.MAPKIT_API_KEY
        MapKitFactory.setApiKey(mapKitApi)

    }
}