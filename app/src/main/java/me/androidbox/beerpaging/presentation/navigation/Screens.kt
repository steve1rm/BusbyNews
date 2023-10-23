package me.androidbox.beerpaging.presentation.navigation

sealed class Screens(val route: String) {
    data object NewsScreen : Screens("news")
    data object NewsDetailScreen : Screens("news_detail")
    data object WebNewsScreen : Screens("web_news")
}
