package me.androidbox.beerpaging.data.newsdatasource.remote

data class NewsDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)