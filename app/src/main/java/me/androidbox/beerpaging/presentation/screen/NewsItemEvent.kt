package me.androidbox.beerpaging.presentation.screen

sealed interface NewsItemEvent {
    data class OnProgressUpdated(val shouldShowProgress: Boolean) : NewsItemEvent
    data class OnShowMoreTitleClicked(val showMoreTitle: Boolean) : NewsItemEvent
    data class OnShowMoreTitleTextClicked(val showMoreOrLessTitleText: ShowMoreOrLessTitleText = ShowMoreOrLessTitleText()) : NewsItemEvent
    data class OnDarkThemeSelected(val isDarkTheme: Boolean): NewsItemEvent
}