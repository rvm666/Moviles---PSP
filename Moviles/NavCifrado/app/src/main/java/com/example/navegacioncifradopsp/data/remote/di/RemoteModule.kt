package com.example.navegacioncifradopsp.data.remote.di

import com.example.navegacioncifradopsp.BuildConfig
import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.data.remote.utils.AuthInterceptor
import com.example.navegacioncifradopsp.data.remote.apiService.ProduccionesApi
import com.example.navegacioncifradopsp.data.remote.apiService.UsuariosApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named(Constantes.PRODUCCIONES)
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    @Named(Constantes.USUARIOS)
    fun provideRetrofitUsuarios(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_PLACEHOLDER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideProduccionesApi(@Named(Constantes.PRODUCCIONES) retrofit: Retrofit): ProduccionesApi {
        return retrofit.create(ProduccionesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuariosApi(@Named(Constantes.USUARIOS) retrofit: Retrofit): UsuariosApi {
        return retrofit.create(UsuariosApi::class.java)
    }
}