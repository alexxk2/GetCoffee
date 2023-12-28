// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.devtools.ksp") version "1.8.22-1.0.11" apply false
    id ("com.google.dagger.hilt.android") version "2.50" apply false
}