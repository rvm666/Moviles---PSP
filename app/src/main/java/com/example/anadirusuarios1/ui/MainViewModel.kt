package com.example.anadirusuarios1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val aniadirProduccionUseCase: AnadirProduccionUseCase
) : ViewModel() {

    private var _state : MutableLiveData<MainState> = MutableLiveData(MainState())
    val state : LiveData<MainState> get() = _state


    fun clickBotonGuardar(produccion: Produccion){
        viewModelScope.launch{
            aniadirProduccionUseCase(produccion)
            _state.value = _state.value?.copy(produccion = produccion)
        }

    }

    class MainViewModelFactory(
        private val aniadirProduccionUseCase: AnadirProduccionUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")

                return MainViewModel(aniadirProduccionUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}