package com.example.gestionproduccionesnavegacion.ui.InfoProduccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anadirusuarios1.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetProduccionById
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.UpdateProduccion
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.UpdateProduccionUsuario
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class InfoProduccionViewModel @Inject constructor(
    private val getProduccionById: GetProduccionById,
    private val updateProduccion: UpdateProduccion,
    private val updateProduccionUsuario: UpdateProduccionUsuario
): ViewModel() {
    var state : MutableLiveData<InfoProduccionState> = MutableLiveData(InfoProduccionState())
        private set


    fun getProduccion(id: Int) {
        viewModelScope.launch {
            val produccion = getProduccionById(id)
            state.value = state.value?.copy(produccion = produccion, isEnable = produccion.esPelicula == false)
        }

    }

    fun updateProduccion(produccion: Produccion){
        viewModelScope.launch {
            if(updateProduccion.invoke(produccion)){
                state.value = state.value?.copy(
                    produccion = produccion,
                    uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_ACTUALIZADA))
            } else {
                state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_ACTUALIZAR_PRODUCCION))
            }
        }
    }

    fun updateProduccionUsuario(produccion: Produccion, idUsuario: Int){
        viewModelScope.launch {
            if(updateProduccionUsuario.invoke(produccion, idUsuario)){
                state.value = state.value?.copy(
                    produccion = produccion,
                    uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_USUARIO_ACTUALIZADA))
            } else {
                state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_ACTUALIZAR_PRODUCCION_USUARIO))
            }
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }
}