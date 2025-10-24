package com.example.anadirusuarios1.ui.pantallaAniadir

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.ui.common.UiEvent
import com.example.anadirusuarios1.ui.common.Constantes
class MainViewModel(
    private val aniadirProduccionUseCase: AnadirProduccionUseCase,
) : ViewModel() {

    var state : MutableLiveData<MainState> = MutableLiveData(MainState())
        private set



    fun clickBotonGuardar(produccion: Produccion){
        if(aniadirProduccionUseCase.invoke(produccion)) {
            state.value = state.value?.copy(
                produccion = produccion,
                uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_GUARDADA))
        } else {
            state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_GUARDAR_PRODUCCION))
        }
    }




    fun limpiarPantalla(){
        val produccion = Produccion(null, "Nombre", "Director",
            null,
            null,
            "Género",
            "Pais",
            0.0
        )
        state.value = state.value?.copy(produccion = produccion)
    }

    fun esPelicula(isCheked: Boolean){
        if(isCheked == true){
            state.value = state.value?.copy(isEnable = false)
        } else {
            state.value = state.value?.copy(isEnable = true)
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }



    class MainViewModelFactory(
        private val aniadirProduccionUseCase: AnadirProduccionUseCase,
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