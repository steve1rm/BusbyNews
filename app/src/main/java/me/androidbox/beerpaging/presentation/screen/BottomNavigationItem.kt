package me.androidbox.beerpaging.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import me.androidbox.beerpaging.presentation.navigation.Screens

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasInfo: Boolean,
    val infoCount: Int? = null
)

enum class NavigationType(val type: String, val screen: String) {
    HOME("Home", Screens.NewsScreen.route),
    NEWS("News", Screens.NewsDetailScreen.route),
    SETTINGS("Settings", Screens.WebNewsScreen.route)
}

val listOfNavigationItem = listOf(
    BottomNavigationItem(
        title = NavigationType.HOME.type,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasInfo = false),

    BottomNavigationItem(
        title = NavigationType.NEWS.type,
        selectedIcon = Icons.Filled.Info,
        unSelectedIcon = Icons.Outlined.Info,
        hasInfo = false,
        infoCount = 0),

    BottomNavigationItem(
        title = NavigationType.SETTINGS.type,
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasInfo = true))


