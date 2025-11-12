package com.example.anadirusuarios1.ui.pantallaInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.ActualizarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetAllProduccionesUseCase
import com.example.anadirusuarios1.ui.common.UiEvent
import com.example.anadirusuarios1.ui.common.Constantes

class InfoMainViewModel(
    private val getProducciones: GetAllProduccionesUseCase,
    private val actualizarProduccionUseCase: ActualizarProduccionUseCase
) : ViewModel() {

    var _state : MutableLiveData<InfoState> = MutableLiveData(
        InfoState(
            produccion = Produccion(
                esPelicula = false,
                nombre = "",
                director = "",
                numeroSeason = 0,
                fechaLanzamiento = null,
                genero = "",
                pais = "",
                valoracion = 0.0
            ),
        )
    )
    val state: MutableLiveData<InfoState> get() = _state

    fun getProduccion(nombre: String){
        val producciones = getProducciones()
        val produccion = producciones.find {it.nombre == nombre}
        if (produccion == null){
            _state.value = _state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_NO_ENCONTRADA))
        } else
            _state.value = _state.value?.copy(produccion = produccion, isEnable = produccion.esPelicula == false)
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
    fun updateProduccion(produccion: Produccion){
        val actualizada =  actualizarProduccionUseCase(_state.value?.produccion?.nombre ?: "", produccion)
        if (actualizada){
            _state.value = _state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_ACTUALIZADA), produccion = produccion)
        } else {
            _state.value = _state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_ACTUALIZAR_PRODUCCION))
        }
    }

    fun limpiarMensaje(){
        _state.value = _state.value?.copy(uiEvent = null)
    }

    class InfoMainViewModelFactory(
        private val getProducciones: GetAllProduccionesUseCase,
        private val actualizarProduccionUseCase: ActualizarProduccionUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InfoMainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InfoMainViewModel(getProducciones, actualizarProduccionUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}