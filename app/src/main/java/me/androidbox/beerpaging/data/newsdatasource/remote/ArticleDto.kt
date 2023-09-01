package me.androidbox.beerpaging.data.newsdatasource.remote

data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val source: SourceDto,
)