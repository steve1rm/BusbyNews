package me.androidbox.beerpaging.presentation.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.androidbox.beerpaging.data.newsdatasource.local.db.NewsDatabase
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.data.newsdatasource.remote.InterceptorApiKey
import me.androidbox.beerpaging.data.newsdatasource.remote.NewsApi
import me.androidbox.beerpaging.data.newsdatasource.remote.NewsRemoteMediator
import me.androidbox.beerpaging.presentation.NewsApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
fun interface AppModule {

    @Binds
    @Singleton
    fun bindsInterceptorApiKey(interceptorApiKey: InterceptorApiKey): Interceptor

    companion object {
        @Provides
        @Singleton
        fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
            return Room.databaseBuilder(
                context,
                NewsDatabase::class.java,
                "news.db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideKotlinMoshiConvertor(): Moshi {
            return Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(interceptorApiKey: InterceptorApiKey): OkHttpClient {
            return OkHttpClient
                .Builder()
       //         .addInterceptor(interceptorApiKey)
                .build()
        }

        @Provides
        @Singleton
        fun provideNewsRemoteApi(okHttpClient: OkHttpClient, moshi: Moshi): NewsApi {
            return Retrofit.Builder()
                .baseUrl(NewsApi.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create()
        }

        @OptIn(ExperimentalPagingApi::class)
        @Provides
        @Singleton
        fun provideNewsPager(newsDatabase: NewsDatabase, newsApi: NewsApi): Pager<Int, ArticleEntity> {
            return Pager(
                config = PagingConfig(pageSize = 20),
                remoteMediator = NewsRemoteMediator(
                    newsDatabase = newsDatabase,
                    newsApi = newsApi
                ),
                pagingSourceFactory = {
                    newsDatabase.newsDao.articlePagingSource()
                }
            )
        }

        @Provides
        @Singleton
        fun provideApplicationContext(@ApplicationContext applicationContext: ApplicationContext): ApplicationContext {
            return applicationContext as ApplicationContext
        }
    }
}