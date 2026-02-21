package com.example.navegacioncifradopsp.ui.listaProducciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Produccion
import com.example.navegacioncifradopsp.domain.usecase.BorrarProduccionUseCase
import com.example.navegacioncifradopsp.domain.usecase.produccionesUseCase.GetAllProduccionesUseCase
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
class LisaProduccionesViewModel @Inject constructor(
    private val getAllProduccionesByUser: GetAllProduccionesUseCase,
    private val borrarProduccionUseCase: BorrarProduccionUseCase,
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
        viewModelScope.launch {
            val produccioness = getAllProduccionesByUser.invoke("nose")
            when(produccioness){
                is NetworkResult.Success -> {
                    _state.update {it.copy(producciones = produccioness.data)}
                }
                is NetworkResult.Error -> sendEvent(UiEvent.ShowSnackbar(produccioness.message))
            }
        }
    }

    fun delete(produccion: Produccion){
        viewModelScope.launch {
            val guardado = borrarProduccionUseCase.invoke(produccion)
            when(guardado){
                is NetworkResult.Success -> {
                    cargarProducciones()
                    sendEvent(UiEvent.ShowSnackbar(Constantes.PRODUCCION_ELIMINADA))
                }
                is NetworkResult.Error -> sendEvent(UiEvent.ShowSnackbar(guardado.message))
            }
        }


    }


}