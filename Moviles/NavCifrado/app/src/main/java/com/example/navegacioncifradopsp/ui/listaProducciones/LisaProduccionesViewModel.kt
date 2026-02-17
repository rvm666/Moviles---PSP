package com.example.navegacioncifradopsp.ui.listaProducciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.usecase.ActualizarProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.AnadirProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.BorrarProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.produccionesUseCase.GetAllProduccionesUseCase
import com.example.navegacioncifradopsp.domain.usecase.produccionesUseCase.GetProduccionByIdUseCase
import com.example.navegacioncifradopsp.ui.pantallaProducción.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LisaProduccionesViewModel @Inject constructor(
    val getAllProduccionesByUser: GetAllProduccionesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(ListaProduccionesState())
    val state: StateFlow<ListaProduccionesState> = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


    fun cargarProducciones(){

    }

}