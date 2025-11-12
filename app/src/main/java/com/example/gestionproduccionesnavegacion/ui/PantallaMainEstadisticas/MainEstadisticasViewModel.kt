package com.example.gestionproduccionesnavegacion.ui.PantallaMainEstadisticas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class MainEstadisticasViewModel @Inject constructor(

): ViewModel() {
    var state : MutableLiveData<MainEstadisticasState> = MutableLiveData(MainEstadisticasState())
        private set


    class MainEstadisticasViewModelFactory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainEstadisticasViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainEstadisticasViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}