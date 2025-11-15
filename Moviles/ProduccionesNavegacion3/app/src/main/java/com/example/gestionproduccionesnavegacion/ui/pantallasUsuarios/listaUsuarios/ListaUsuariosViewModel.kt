package com.example.gestionproduccionesnavegacion.ui.pantallasUsuarios.listaUsuarios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.useCase.usuarios.DeleteUsuario
import com.example.gestionproduccionesnavegacion.domain.useCase.usuarios.GetAllUsuariosUseCase
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ListaUsuariosViewModel @Inject constructor(
    private val getAllUsuariosUseCase: GetAllUsuariosUseCase,
    private val borrarUsuario: DeleteUsuario
): ViewModel() {

    var state : MutableLiveData<ListaUsuariosState> = MutableLiveData(ListaUsuariosState())
        private set


    fun cargarUsuarios(){
        viewModelScope.launch {
            val usuarios = getAllUsuariosUseCase()
            state.value = ListaUsuariosState(usuarios = usuarios)
        }
    }

    fun borrar(usuarioId: Int){
        viewModelScope.launch {
            if (borrarUsuario.invoke(usuarioId)) {
                state.value =
                    state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.USUARIO_BORRADO))
                cargarUsuarios()
            } else {
                state.value =
                    state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_BORRAR_USUARIO))
            }
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }


}