package com.example.gestionproduccionesnavegacion.ui.CrearUsuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anadirusuarios1.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios.InsertarUsuario
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent
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
            if (insertarUsuario(usuario)) {
                state.value = state.value?.copy(
                    usuario = usuario,
                    uiEvent = UiEvent.ShowSnackbar(Constantes.USUARIO_GUARDADO)
                )
            } else {
                state.value =
                    state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_GUARDAR_USUARIO))

            }
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }
}