package com.example.navegacioncifradopsp.ui.pantallaProducción

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Produccion
import com.example.navegacioncifradopsp.domain.usecase.ActualizarProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.AnadirProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.BorrarProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.produccionesUseCase.GetProduccionByIdUseCase
import com.example.navegacioncifradopsp.ui.pantallaProducción.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val aniadirProduccionUseCase: AnadirProduccionUseCase,
    private val getProduccionUseCase: GetProduccionByIdUseCase,
    private val actualizarProduccionUseCase: ActualizarProduccionUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(MainState())
    val state : StateFlow<MainState> = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun clickBotonGuardar(produccion: Produccion){
        if (produccion.nombre.isBlank()) {
            sendEvent(UiEvent.ShowSnackbar(Constantes.NOMBRE_OBLIGATORIO))
            return
        }


        if (produccion.director.isBlank()) {
            sendEvent(UiEvent.ShowSnackbar(Constantes.DIRECTOR_OBLIGATORIO))
            return
        }

        viewModelScope.launch {
            val result = aniadirProduccionUseCase.invoke(produccion)
            when(result){
                is NetworkResult.Success -> {
                    _state.update { it.copy(produccion = produccion) }
                    _uiEvent.send(UiEvent.ShowSnackbar(Constantes.PRODUCCION_GUARDADA))
                    _uiEvent.send(UiEvent.NavigateBack)
                }
                is NetworkResult.Error -> _uiEvent.send(UiEvent.ShowSnackbar(result.message))
            }
        }
    }


    fun actualizarProduccion(produccion: Produccion) {
        val id = _state.value.produccion.id
        if (produccion.nombre.isBlank()) {
            sendEvent(UiEvent.ShowSnackbar(Constantes.NOMBRE_OBLIGATORIO))
            return
        }

        if (produccion.director.isBlank()) {
            sendEvent(UiEvent.ShowSnackbar(Constantes.DIRECTOR_OBLIGATORIO))
            return
        }

        viewModelScope.launch {
            val result = actualizarProduccionUseCase(id, produccion)
            when(result){
                is NetworkResult.Success -> {
                    _state.update{ it.copy(produccion = produccion) }
                    _uiEvent.send(UiEvent.ShowSnackbar(Constantes.PRODUCCION_ACTUALIZADA))
                    _uiEvent.send(UiEvent.NavigateBack)
                }
                is NetworkResult.Error -> _uiEvent.send(UiEvent.ShowSnackbar(result.message))
            }
        }
    }

    fun cargarProduccion(id: Int){

    }

    fun limpiarPantalla(){
        val produccion = Produccion(
            nombre = "",
            director = "",
            lanzamiento = 0,
            genero = ""

        )
        _state.update{it.copy(produccion = produccion)}
    }

}