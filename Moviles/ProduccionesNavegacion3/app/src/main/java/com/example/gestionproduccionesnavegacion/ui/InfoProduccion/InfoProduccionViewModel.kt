package com.example.gestionproduccionesnavegacion.ui.InfoProduccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestionproduccionesnavegacion.ui.AniadirProduccion.AniadirProduccionState
import com.example.gestionproduccionesnavegacion.ui.AniadirProduccion.AniadirProduccionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class InfoProduccionViewModel @Inject constructor(

): ViewModel() {
    var state : MutableLiveData<InfoProduccionState> = MutableLiveData(InfoProduccionState())
        private set


    class InfoProduccionViewModelFactory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InfoProduccionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InfoProduccionViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}