package com.example.gestionproduccionesnavegacion.ui.pantallasUsuarios.editarUsuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetNumProduccionesPendientes
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetNumProduccionesVistas
import com.example.gestionproduccionesnavegacion.domain.useCase.usuarios.GetUsuarioById
import com.example.gestionproduccionesnavegacion.domain.useCase.usuarios.UpdateUsuario
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class EditarUsuarioViewModel @Inject constructor(
    private val getUsuarioById: GetUsuarioById,
    private val actualizarUsuario: UpdateUsuario,
    private val getNumProduccionesVistas: GetNumProduccionesVistas,
    private val getNumProduccionesPendientes: GetNumProduccionesPendientes,
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


    fun getProduccionesPendientes(id: Int){
        viewModelScope.launch {
            val total = getNumProduccionesPendientes(id)
            state.value = state.value?.copy(pendientes = total)
        }
    }

    fun getProduccionesVistas(id: Int){
        viewModelScope.launch {
            val total = getNumProduccionesVistas(id)
            state.value = state.value?.copy(vistas = total)
        }
    }
}