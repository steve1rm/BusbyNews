package me.androidbox.beerpaging.data.newsdatasource.mapper

import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.data.newsdatasource.remote.ArticleDto
import me.androidbox.beerpaging.domain.ArticleModel

fun ArticleModel.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
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

fun ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = this.author.orEmpty(),
        content = this.content.orEmpty(),
        description = this.description.orEmpty(),
        publishedAt = this.publishedAt.orEmpty(),
        sourceId = this.source.id.orEmpty(),
        sourceName = this.source.name.orEmpty(),
        title = this.title.orEmpty(),
        url = this.url.orEmpty(),
        urlToImage = this.urlToImage.orEmpty()
    )
}
