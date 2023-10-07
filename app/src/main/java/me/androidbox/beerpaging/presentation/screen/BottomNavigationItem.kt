package me.androidbox.beerpaging.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasInfo: Boolean,
    val infoCount: Int? = null
)


val listOfNavigationItem = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasInfo = false),

    BottomNavigationItem(
        title = "News",
        selectedIcon = Icons.Filled.Info,
        unSelectedIcon = Icons.Outlined.Info,
        hasInfo = false,
        infoCount = 45),

    BottomNavigationItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasInfo = true))


