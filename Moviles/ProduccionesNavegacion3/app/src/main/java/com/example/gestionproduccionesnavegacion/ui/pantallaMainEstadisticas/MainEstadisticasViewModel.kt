package com.example.gestionproduccionesnavegacion.ui.pantallaMainEstadisticas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetNumTotalProducciones
import com.example.gestionproduccionesnavegacion.domain.useCase.usuarios.GetNumTotalUsuarios
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainEstadisticasViewModel @Inject constructor(
    private val getNumTotalProducciones: GetNumTotalProducciones,
    private val getNumTotalUsuarios: GetNumTotalUsuarios,
): ViewModel() {
    var state : MutableLiveData<MainEstadisticasState> = MutableLiveData(MainEstadisticasState())
        private set

    fun getTotalUsuarios(){
        viewModelScope.launch{
            val total = getNumTotalUsuarios()
            state.value = state.value?.copy(usuarios = total)
        }
    }

    fun getTotalProducciones(){
        viewModelScope.launch{
            val total = getNumTotalProducciones()
            state.value = state.value?.copy(producciones = total)
        }
    }



}