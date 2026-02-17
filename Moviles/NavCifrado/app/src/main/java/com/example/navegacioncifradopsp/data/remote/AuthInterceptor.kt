package com.example.navegacioncifradopsp.data.remote

import com.example.navegacioncifradopsp.BuildConfig
import com.example.navegacioncifradopsp.common.Constantes
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter(Constantes.API_KEY, BuildConfig.API_KEY)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }

}