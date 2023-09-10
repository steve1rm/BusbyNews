package me.androidbox.beerpaging.presentation.screen

data class NewsItemState(
    val shouldShowProgress: Boolean = false,
    val showMoreTitleClicked: Boolean = false,
    val shouldShowMoreTitle: Boolean = false,
    val shouldMoreDescriptionClicked: Boolean = false,
    val shouldShowMoreDescription: Boolean = false
)
