package me.androidbox.beerpaging.data.newsdatasource.mapper

import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.domain.ArticleModel

fun ArticleEntity.toArticleModel(): ArticleModel {
    return ArticleModel(
        id = this.articleId,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        sourceId = this.sourceId,
        sourceName = this.sourceName,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}
