package me.androidbox.beerpaging.data.newsdatasource.remote

import retrofit2.http.GET
import retrofit2.http.Query

fun interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeedLines(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsDto

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "d96c3612442342fc8295fcdf3184970b"
    }
}
