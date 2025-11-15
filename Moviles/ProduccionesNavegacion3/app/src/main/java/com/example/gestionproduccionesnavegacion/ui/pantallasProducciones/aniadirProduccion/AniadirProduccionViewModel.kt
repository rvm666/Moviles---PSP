package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.aniadirProduccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.InsertarProduccion
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class AniadirProduccionViewModel @Inject constructor(
    private val insertarProduccion: InsertarProduccion
) : ViewModel() {

    var state : MutableLiveData<AniadirProduccionState> = MutableLiveData(AniadirProduccionState())
        private set

    fun guardarProduccion(produccion: Produccion){
        viewModelScope.launch {
            if(insertarProduccion(produccion)){
                    state.value = state.value?.copy(
                        produccion = produccion,
                        uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_GUARDADA))
                    delay(1500)
                } else {
                    state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_GUARDAR_PRODUCCION))
                }
        }
    }

    fun esPelicula(isCheked: Boolean){
        if(isCheked){
            state.value = state.value?.copy(isEnable = false)
        } else {
            state.value = state.value?.copy(isEnable = true)
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }

}