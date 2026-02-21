package com.example.navegacioncifradopsp.ui.pantallaRegistrar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Usuario
import com.example.navegacioncifradopsp.ui.listaProducciones.ListaProduccionesState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrarViewModel @Inject constructor(

) : ViewModel() {

    private var _state = MutableStateFlow(RegistrarState())
    val state: StateFlow<RegistrarState> = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


    fun register(usuario: Usuario){
        viewModelScope.launch {
            val result =
        }
    }
}