package me.androidbox.beerpaging.data.newsdatasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import me.androidbox.beerpaging.data.newsdatasource.local.db.NewsDatabase
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.data.newsdatasource.local.entity.PaginationInfo
import me.androidbox.beerpaging.data.newsdatasource.mapper.toArticleEntity
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsApi
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    // Read the nextPage value from the paginationInfo table
                    val paginationInfo = newsDatabase.paginationInfoDao.getPaginationInfo()
                    paginationInfo?.nextPage ?: 1
                }
            }

            val newsDto = newsApi.getHeedLines(
                pageSize = 5,
                page = page,
                country = "us",
                apiKey = "d96c3612442342fc8295fcdf3184970b")

            newsDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    newsDatabase.newsDao.clearAllArticle()
                    newsDatabase.paginationInfoDao.clearAll()
                }
                newsDatabase.newsDao.upsertAllArticles(
                    articleEntity = newsDto.articles
                        .filterNullArticles()
                        .map { articleDto ->
                        articleDto.toArticleEntity()
                    })
                newsDatabase.paginationInfoDao.upsertPaginationInfo(PaginationInfo(nextPage = page + 1))
            }

            MediatorResult.Success(
                endOfPaginationReached = newsDto.articles.isEmpty()
            )
        }
        catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
        catch (httpException: HttpException) {
            MediatorResult.Error(httpException)
        }
    }

    private fun List<ArticleDto>.filterNullArticles(): List<ArticleDto> {
        return this.filter { articleDto ->
            articleDto.author != null &&
                    articleDto.content != null &&
                    articleDto.description != null &&
                    articleDto.title != null &&
                    articleDto.publishedAt != null
        }
    }
}
