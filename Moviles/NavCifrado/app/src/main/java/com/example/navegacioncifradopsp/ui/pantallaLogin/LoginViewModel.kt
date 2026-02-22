package com.example.navegacioncifradopsp.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.usecase.authUseCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun login(username: String, password: String){
        viewModelScope.launch {
            val result = loginUseCase(username, password)
            when(result){
                is NetworkResult.Success -> {
                    sendEvent(UiEvent.ShowSnackbar("Login exitoso"))
                    sendEvent(UiEvent.NavigateToHome)
                }
                is NetworkResult.Error -> {
                    sendEvent(UiEvent.ShowSnackbar(result.message))
                }
            }
        }
    }
}