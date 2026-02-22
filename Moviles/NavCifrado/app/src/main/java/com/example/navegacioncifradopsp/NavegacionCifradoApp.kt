package com.example.navegacioncifradopsp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")
@HiltAndroidApp
class NavegacionCifradoApp : Application(){

    override fun onCreate() {
        super.onCreate()
       Timber.plant(Timber.DebugTree())

    }
}