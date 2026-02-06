package com.example.composeapp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.common.Constantes
import com.example.composeapp.common.UiEvent
import com.example.composeapp.domain.model.Produccion
import com.example.composeapp.domain.usecase.ActualizarProduccionUseCase
import com.example.composeapp.domain.usecase.AnadirProduccionUseCase
import com.example.composeapp.domain.usecase.BorrarProduccionUseCase
import com.example.composeapp.domain.usecase.GetProduccionUseCase
import com.example.composeapp.domain.usecase.SizeProduccionesUseCase
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
    private val getProduccionUseCase: GetProduccionUseCase,
    private val borrarProduccionUseCase: BorrarProduccionUseCase,
    private val sizeProduccionesUseCase: SizeProduccionesUseCase,
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

        if(aniadirProduccionUseCase.invoke(produccion)) {
            _state.update{it.copy(produccion = produccion)}
            limpiarPantalla()
            sendEvent(UiEvent.ShowSnackbar(Constantes.PRODUCCION_GUARDADA))
        } else {
            sendEvent(UiEvent.ShowSnackbar(Constantes.ERROR))
        }
    }

    fun clickBotonBorrar(produccion: Produccion){
        if(borrarProduccionUseCase.invoke(produccion)){
            sendEvent(UiEvent.ShowSnackbar(Constantes.PRODUCCION_ELIMINADA))
        } else {
            sendEvent(UiEvent.ShowSnackbar(Constantes.ERROR))
        }
    }
    fun actualizarProduccion(produccion: Produccion) {
        val id = _state.value.indiceProduccion
        if (produccion.nombre.isBlank()) {
            sendEvent(UiEvent.ShowSnackbar(Constantes.NOMBRE_OBLIGATORIO))
            return
        }


        if (produccion.director.isBlank()) {
            sendEvent(UiEvent.ShowSnackbar(Constantes.DIRECTOR_OBLIGATORIO))
            return
        }
        actualizarProduccionUseCase(id, produccion)
        limpiarPantalla()
        _state.update{it.copy(produccion = produccion)}
    }
    fun limpiarPantalla(){
        val produccion = Produccion(
            esPelicula = null,
            nombre = "",
            director = "",
            lanzamiento = null,
            numeroTemporadas = null,
            genero = "",
            pais = "",
            valoracion = 0.0
        )
        _state.update{it.copy(produccion = produccion)}
    }

    fun esPelicula(isCheked: Boolean){
        if(isCheked){
            _state.update{it.copy(isEnable = false)}
        } else {
            _state.update{it.copy(isEnable = true)}
        }
    }


    fun siguienteProduccion(){
        val indice = _state.value.indiceProduccion
        val nuevoIndice = if (indice >= sizeProduccionesUseCase() -1){
           0
        } else {
           indice +1
        }
        val produccion = getProduccionUseCase.invoke(nuevoIndice)
        _state.update{it.copy(produccion = produccion, indiceProduccion = nuevoIndice)}
    }
    fun anteriorProduccion(){
        val indice = _state.value.indiceProduccion

        val nuevoIndice :Int = if (indice == 0){
            sizeProduccionesUseCase() - 1
        } else {
            indice -1
        }

        val produccion = getProduccionUseCase.invoke(nuevoIndice)
        _state.update{it.copy(produccion = produccion, indiceProduccion = nuevoIndice)}

    }


}