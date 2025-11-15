package com.example.gestionproduccionesnavegacion.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.gestionproduccionesnavegacion.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database5")
            .createFromAsset("database/myappDB.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }


    @Provides
    fun provideProduccionesDao(database: AppDatabase) = database.produccionDao()

    @Provides
    fun provideUsuariosDao(database: AppDatabase) = database.usuarioDao()
}