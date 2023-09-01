package me.androidbox.beerpaging.data.newsdatasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.data.newsdatasource.local.entity.PaginationInfo
import me.androidbox.beerpaging.data.newsdatasource.local.typeconverter.ArticleListTypeConverter

@Database(
    entities = [ArticleEntity::class, PaginationInfo::class],
    version = 2)
@TypeConverters(ArticleListTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val paginationInfoDao: PaginationInfoDao
}