package me.androidbox.beerpaging.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {
    private var isDarkMode by mutableStateOf(false)

    fun toggleDarkThemeOff() {
        isDarkMode = !isDarkMode
    }
}
