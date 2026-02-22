package com.example.navegacioncifradopsp.data.remote.di

import com.example.navegacioncifradopsp.BuildConfig
import com.example.navegacioncifradopsp.data.remote.apiService.AuthApiService
import com.example.navegacioncifradopsp.data.remote.utils.AuthInterceptor
import com.example.navegacioncifradopsp.data.remote.apiService.ProduccionesApi
import com.example.navegacioncifradopsp.data.remote.apiService.UsuariosApi
import com.example.navegacioncifradopsp.data.remote.utils.AuthAuthenticator
import com.example.navegacioncifradopsp.data.remote.utils.TokenManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import dagger.Lazy


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
        logginInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logginInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideProduccionesApi(retrofit: Retrofit): ProduccionesApi {
        return retrofit.create(ProduccionesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuariosApi( retrofit: Retrofit): UsuariosApi {
        return retrofit.create(UsuariosApi::class.java)
    }

    @Provides @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)


    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager,authApiService: Lazy<AuthApiService>): AuthAuthenticator =
        AuthAuthenticator(tokenManager,authApiService)

}