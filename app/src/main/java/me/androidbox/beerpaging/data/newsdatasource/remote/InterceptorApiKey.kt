package me.androidbox.beerpaging.data.newsdatasource.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class InterceptorApiKey @Inject constructor(): Interceptor {

    private companion object {
        const val API_KEY_HEADER = "x-api-key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(API_KEY_HEADER, NewsApi.API_KEY)
            .build()

        return chain.proceed(request)
    }
}