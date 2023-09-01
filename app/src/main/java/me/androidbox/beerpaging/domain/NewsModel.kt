package me.androidbox.beerpaging.domain

data class NewsModel(
    val articles: List<ArticleModel>,
    val status: String,
    val totalResults: Int
)