package me.androidbox.beerpaging.data.newsdatasource.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity

@Dao
interface NewsDao {
    @Upsert
    suspend fun upsertAllArticles(articleEntity: List<ArticleEntity>)

    @Query("SELECT * FROM ArticleEntity")
    fun articlePagingSource(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM ARTICLEENTITY")
    suspend fun clearAllArticle()
}