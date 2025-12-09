package com.example.gestionproduccionesnavegacion.ui.EditarUsuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class EditarUsuarioViewModel @Inject constructor(

): ViewModel() {
    var state : MutableLiveData<EditarUsuarioState> = MutableLiveData(EditarUsuarioState())
        private set


    class EditarUsuarioViewModelFactory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditarUsuarioViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EditarUsuarioViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}