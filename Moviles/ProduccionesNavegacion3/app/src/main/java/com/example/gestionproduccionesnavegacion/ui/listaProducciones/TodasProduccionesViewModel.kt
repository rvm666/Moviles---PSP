package com.example.gestionproduccionesnavegacion.ui.listaProducciones

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetAllProduccionesUseCase
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetProduccionesVistas
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetProduccionesPendientes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TodasProduccionesViewModel @Inject constructor(
    private val getAllProduccionesUseCase: GetAllProduccionesUseCase,
    private val getProduccionesVistas: GetProduccionesVistas,
    private val getProduccionesPendientes: GetProduccionesPendientes,
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

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }

}