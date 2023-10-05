package me.androidbox.beerpaging.presentation.screen

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasInfo: Boolean,
    val infoCount: Int? = null
)
