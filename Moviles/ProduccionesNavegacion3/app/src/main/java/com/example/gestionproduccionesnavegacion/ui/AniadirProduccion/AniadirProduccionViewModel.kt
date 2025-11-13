package com.example.gestionproduccionesnavegacion.ui.AniadirProduccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.InsertarProduccion
import dagger.hilt.android.lifecycle.HiltViewModel
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
            insertarProduccion(produccion)
        }
    }

    fun esPelicula(isCheked: Boolean){
        if(isCheked == true){
            state.value = state.value?.copy(isEnable = false)
        } else {
            state.value = state.value?.copy(isEnable = true)
        }
    }

}