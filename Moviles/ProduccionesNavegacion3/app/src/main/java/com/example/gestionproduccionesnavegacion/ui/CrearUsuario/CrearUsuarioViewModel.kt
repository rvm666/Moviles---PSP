package com.example.gestionproduccionesnavegacion.ui.CrearUsuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios.InsertarUsuario
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CrearUsuarioViewModel @Inject constructor(
    private val insertarUsuario: InsertarUsuario
): ViewModel() {

    var state : MutableLiveData<CrearUsuarioState> = MutableLiveData(CrearUsuarioState())
        private set


    fun guardarUsuario(usuario : Usuario){
        viewModelScope.launch {
            insertarUsuario(usuario)
        }
    }
}