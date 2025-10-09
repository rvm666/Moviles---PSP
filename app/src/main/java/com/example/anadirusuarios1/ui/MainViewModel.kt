package com.example.anadirusuarios1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val aniadirProduccionUseCase: AnadirProduccionUseCase,
    private val getProduccionUseCase: GetProduccionUseCase
) : ViewModel() {

    private var _state : MutableLiveData<MainState> = MutableLiveData(MainState())
    val state : LiveData<MainState> get() = _state



    fun clickBotonGuardar(produccion: Produccion){
        if(aniadirProduccionUseCase.invoke(produccion)) {
            _state.value = _state.value?.copy(produccion = produccion, mensaje = "Producción añadida")
        } else {
            _state.value = _state.value?.copy(mensaje = "ERROR")
        }
    }

    fun limpiarMensaje(){
        _state.value = _state.value?.copy(mensaje = null)
    }
    fun siguienteProduccion(){
        val indice = _state.value?.indiceProduccion ?: 0

        val produccion = getProduccionUseCase.invoke(indice)
        _state.value = _state.value?.copy(produccion = produccion, indiceProduccion = indice +1)
    }
    class MainViewModelFactory(
        private val aniadirProduccionUseCase: AnadirProduccionUseCase,
        private val getProduccionUseCase: GetProduccionUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(aniadirProduccionUseCase, getProduccionUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}