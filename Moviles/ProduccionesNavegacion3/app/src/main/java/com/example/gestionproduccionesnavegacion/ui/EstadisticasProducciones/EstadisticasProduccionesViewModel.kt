package com.example.gestionproduccionesnavegacion.ui.EstadisticasProducciones

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestionproduccionesnavegacion.ui.AniadirProduccion.AniadirProduccionState
import com.example.gestionproduccionesnavegacion.ui.AniadirProduccion.AniadirProduccionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class EstadisticasProduccionesViewModel @Inject constructor(

): ViewModel() {
    var state : MutableLiveData<EstadisticasProduccionesState> = MutableLiveData(EstadisticasProduccionesState())
        private set


    class EstadisticasProduccionesViewModelFactory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EstadisticasProduccionesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EstadisticasProduccionesViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}