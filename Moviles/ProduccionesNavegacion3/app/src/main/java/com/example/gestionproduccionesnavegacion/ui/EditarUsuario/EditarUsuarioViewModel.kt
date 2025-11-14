package com.example.gestionproduccionesnavegacion.ui.EditarUsuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anadirusuarios1.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios.GetUsuarioById
import com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios.UpdateUsuario
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class EditarUsuarioViewModel @Inject constructor(
    private val getUsuarioById: GetUsuarioById,
    private val actualizarUsuario: UpdateUsuario
): ViewModel() {
    var state: MutableLiveData<EditarUsuarioState> = MutableLiveData(EditarUsuarioState())
        private set


    fun getUsuario(id: Int) {
        viewModelScope.launch {
            val usuario = getUsuarioById(id)
            state.value = state.value?.copy(usuario = usuario)
        }

    }

    fun updateUsuario(usuario: Usuario) {
        viewModelScope.launch {
            if(actualizarUsuario(usuario)){
                state.value = state.value?.copy(
                    usuario = usuario,
                    uiEvent = UiEvent.ShowSnackbar(Constantes.USUARIO_ACTUALIZADO))
            } else {
                state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_ACTUALIZAR_USUARIO))
            }
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }

}