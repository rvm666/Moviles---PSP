package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.listaProducciones

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.DeleteProduccion
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetAllProduccionesUseCase
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetProduccionesVistas
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetProduccionesPendientes
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TodasProduccionesViewModel @Inject constructor(
    private val getAllProduccionesUseCase: GetAllProduccionesUseCase,
    private val getProduccionesVistas: GetProduccionesVistas,
    private val getProduccionesPendientes: GetProduccionesPendientes,
    private val borrarProduccion: DeleteProduccion
): ViewModel() {
    var state : MutableLiveData<TodasProduccionesState> = MutableLiveData(TodasProduccionesState())
        private set

    fun cargarProducciones(){
        viewModelScope.launch {
            val producciones = getAllProduccionesUseCase()
            state.value = TodasProduccionesState(producciones = producciones)
        }
    }

    fun cargarProduccionesVistas(id: Int){
        viewModelScope.launch {
            val producciones = getProduccionesVistas(id)
            state.value = TodasProduccionesState(producciones = producciones)
        }
    }

    fun cargarProduccionesPendientes(id: Int){
        viewModelScope.launch {
            val producciones = getProduccionesPendientes(id)
            state.value = TodasProduccionesState(producciones = producciones)
        }
    }

    fun borrar(produccionId: Int){
        viewModelScope.launch {
            if (borrarProduccion.invoke(produccionId)) {
                state.value =
                    state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_BORRADA))
                cargarProducciones()
            } else {
                state.value =
                    state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_BORRAR_PRODUCCION))
            }
        }
    }


    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }

}