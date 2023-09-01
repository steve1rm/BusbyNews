package me.androidbox.beerpaging.domain

data class ArticleModel(
    val id: Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val sourceId: String,
    val sourceName: String
)