package me.androidbox.beerpaging.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {
    var isDarkMode by mutableStateOf(false)
        private set

    fun toggleDarkThemeOff() {
        isDarkMode = !isDarkMode
    }
}
