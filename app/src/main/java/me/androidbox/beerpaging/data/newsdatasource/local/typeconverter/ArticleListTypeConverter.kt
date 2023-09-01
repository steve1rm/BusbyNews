package me.androidbox.beerpaging.data.newsdatasource.local.typeconverter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity


class ArticleListTypeConverter {
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val articleListType = Types.newParameterizedType(List::class.java, ArticleEntity::class.java)
    private val jsonAdaper = moshi.adapter<List<ArticleEntity>>(articleListType)

    @TypeConverter
    fun fromJson(json: String): List<ArticleEntity> {
        return jsonAdaper.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toJson(listOfArticle: List<ArticleEntity>): String {
        return jsonAdaper.toJson(listOfArticle)
    }
}
