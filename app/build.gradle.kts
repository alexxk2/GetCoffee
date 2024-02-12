import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    // Navigation.SafeArgs
    id("androidx.navigation.safeargs.kotlin")
    //Kotlin symbol processing  for Room
    id("com.google.devtools.ksp")
    //Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.getcoffee"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.getcoffee"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String","MAPKIT_API_KEY","\"${properties.getProperty("MAPKIT_API_KEY")}\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    configurations {
        implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))

    }
}

dependencies {

    val navVersion = "2.6.0"
    val roomVersion = "2.5.2"
    val lifecycleVersion = "2.6.1"
    val coreKtxVersion = "1.12.0"
    val appCompat = "1.6.1"
    val materialVersion = "1.11.0"
    val constraintLayout = "2.1.4"
    val junit = "4.13.2"
    val espresso = "3.5.1"
    val androidxJunit = "1.1.5"
    val fragment = "1.6.2"
    val glideVersion = "4.15.0"
    val gsonVersion = "2.10.1"
    val hiltVersion = "2.50"
    val retrofitVersion ="2.9.0"

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.appcompat:appcompat:$appCompat")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayout")
    testImplementation("junit:junit:$junit")
    androidTestImplementation("androidx.test.ext:junit:$androidxJunit")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")

    // Yandex maps lite - облегченная библиотека, содержит только карту и слой пробок
    implementation("com.yandex.android:maps.mobile:4.3.1-lite")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.fragment:fragment-ktx:$fragment")
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    //Gson
    implementation("com.google.code.gson:gson:$gsonVersion")
    //glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    annotationProcessor("com.github.bumptech.glide:compiler:$glideVersion")
    //Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    // Room libraries
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    //Kotlin symbol processing for Room
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    //Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    //hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    //Retrofit
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
