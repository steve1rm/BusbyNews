package me.androidbox.beerpaging.presentation

import android.app.Application
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltAndroidApp
class NewsApplication : Application() {
    val LocalTheme = compositionLocalOf { false }

    var isDarkMode by mutableStateOf(true)
        private set


/*
    var theme: AppTheme = AppTheme.MODE_AUTO
    val themeStream: StateFlow<AppTheme> = MutableStateFlow(theme)
*/

    private val _appTheme: MutableStateFlow<AppTheme> = MutableStateFlow<AppTheme>(AppTheme.MODE_AUTO)
    val appTheme = _appTheme.asStateFlow()

    fun toggleDarkThemeOff(newAppTheme: AppTheme) {
        _appTheme.update { appTheme ->
            newAppTheme
        }
    }
}
