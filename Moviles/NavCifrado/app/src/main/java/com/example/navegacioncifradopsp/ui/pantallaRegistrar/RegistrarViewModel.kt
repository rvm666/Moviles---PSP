package com.example.navegacioncifradopsp.ui.pantallaRegistrar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Usuario
import com.example.navegacioncifradopsp.domain.usecase.authUseCase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrarViewModel @Inject constructor(
    private val registrarUsuario: RegisterUseCase
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
            val result = registrarUsuario.invoke(usuario)
            when(result){
                is NetworkResult.Success -> {
                    sendEvent(UiEvent.ShowSnackbar("Usuario registrado correctamente"))
                    sendEvent(UiEvent.NavigateBack)
                }
                is NetworkResult.Error -> sendEvent(UiEvent.ShowSnackbar(result.message))
            }
        }
    }
}