package com.example.gestionproduccionesnavegacion.ui.CrearUsuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class CrearUsuarioViewModel @Inject constructor(

): ViewModel() {

    var state : MutableLiveData<CrearUsuarioState> = MutableLiveData(CrearUsuarioState())
        private set


    class CrearUsuarioViewModelFactory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CrearUsuarioViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CrearUsuarioViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}