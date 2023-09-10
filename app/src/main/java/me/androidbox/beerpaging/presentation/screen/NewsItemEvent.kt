package me.androidbox.beerpaging.presentation.screen

sealed interface NewsItemEvent {
    data class OnProgressUpdated(val shouldShow: Boolean) : NewsItemEvent
    data class OnShowMoreTitleClicked(val showMoreTitleClicked: Boolean) : NewsItemEvent
}