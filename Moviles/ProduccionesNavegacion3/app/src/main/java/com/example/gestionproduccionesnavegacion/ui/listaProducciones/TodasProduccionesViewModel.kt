package com.example.gestionproduccionesnavegacion.ui.listaProducciones

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetAllProduccionesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TodasProduccionesViewModel @Inject constructor(
    private val getAllProduccionesUseCase: GetAllProduccionesUseCase
): ViewModel() {
    var state : MutableLiveData<TodasProduccionesState> = MutableLiveData()
        private set
    init{
        cargarProducciones()
    }

    fun cargarProducciones(){

        viewModelScope.launch {
            val producciones = getAllProduccionesUseCase()
            state.value = TodasProduccionesState(producciones = producciones)
        }
    }

}