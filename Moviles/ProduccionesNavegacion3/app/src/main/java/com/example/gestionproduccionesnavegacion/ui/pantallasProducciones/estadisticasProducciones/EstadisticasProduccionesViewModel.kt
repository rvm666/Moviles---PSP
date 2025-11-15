package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.estadisticasProducciones

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetGeneroMasPopular
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetNumTotalProducciones
import com.example.gestionproduccionesnavegacion.domain.useCase.producciones.GetProduccionMasVista
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EstadisticasProduccionesViewModel @Inject constructor(
    private val getNumTotalProducciones: GetNumTotalProducciones,
    private val getProduccionMasVista: GetProduccionMasVista,
    private val getGeneroMasPopular: GetGeneroMasPopular
): ViewModel() {
    var state : MutableLiveData<EstadisticasProduccionesState> = MutableLiveData(EstadisticasProduccionesState())
        private set

    fun getTotalProducciones(){
        viewModelScope.launch {
            val total = getNumTotalProducciones()
            state.value = state.value?.copy(numeroProducciones = total)
        }
    }

    fun getProduccionmasVista(){
        viewModelScope.launch {
            val produccion = getProduccionMasVista() ?: "Ninguna"
            state.value = state.value?.copy(masVistada = produccion)
        }
    }

    fun getGeneroPopular(){
        viewModelScope.launch {
            val genero = getGeneroMasPopular() ?: "Ninguno"
            state.value = state.value?.copy(generoMasVisto = genero)
        }
    }
}