package com.example.gestionproduccionesnavegacion.ui.InfoProduccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetProduccionById
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class InfoProduccionViewModel @Inject constructor(
    private val getProduccionById: GetProduccionById
): ViewModel() {
    var state : MutableLiveData<InfoProduccionState> = MutableLiveData(InfoProduccionState())
        private set


    fun getProduccion(id: Int) {
        viewModelScope.launch {
            val produccion = getProduccionById(id)
            state.value = state.value?.copy(produccion = produccion)
        }

    }
}