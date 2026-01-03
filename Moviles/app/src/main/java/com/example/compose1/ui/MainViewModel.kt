package com.example.anadirusuarios1.ui


import androidx.lifecycle.ViewModel
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.ActualizarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.SizeProduccionesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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



    fun clickBotonGuardar(produccion: Produccion){
        if(aniadirProduccionUseCase.invoke(produccion)) {
            _state.update{it.copy(produccion = produccion, mensaje = "Producción añadida")}
        } else {
            _state.update{it.copy(mensaje = "ERROR")}
        }
    }

    fun clickBotonBorrar(produccion: Produccion){
        if(borrarProduccionUseCase.invoke(produccion)){
            _state.update{it.copy(mensaje = "Produccion borrada")}
        } else {
            _state.update{it.copy(mensaje = "ERROR")}
        }
    }
    fun actualizarProduccion(produccion: Produccion){
        val id = _state.value.indiceProduccion
        actualizarProduccionUseCase(id, produccion)
        _state.update{it.copy(produccion = produccion)}
    }
    fun limpiarPantalla(){
        val produccion = Produccion( null, "Nombre", "Director",
            null,
            null,
            "Género",
            "Pais",
            0.0
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

    fun limpiarMensaje(){
        _state.update{it.copy(mensaje = null)}
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