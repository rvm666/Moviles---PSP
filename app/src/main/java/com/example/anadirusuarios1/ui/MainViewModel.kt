package com.example.anadirusuarios1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.SizeProduccionesUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val aniadirProduccionUseCase: AnadirProduccionUseCase,
    private val getProduccionUseCase: GetProduccionUseCase,
    private val borrarProduccionUseCase: BorrarProduccionUseCase,
    private val sizeProduccionesUseCase: SizeProduccionesUseCase
) : ViewModel() {

    private var _state : MutableLiveData<MainState> = MutableLiveData(MainState())
    val state : LiveData<MainState> get() = _state



    fun clickBotonGuardar(produccion: Produccion){
        if(aniadirProduccionUseCase.invoke(produccion)) {
            _state.value = _state.value?.copy(produccion = produccion, mensaje = "Producción añadida")
        } else {
            _state.value = _state.value?.copy(mensaje = "ERROR")
        }
    }

    fun clickBotonBorrar(produccion: Produccion){
        if(borrarProduccionUseCase.invoke(produccion)){
            _state.value = _state.value?.copy(mensaje = "Produccion borrada")
        } else {
            _state.value = _state.value?.copy(mensaje = "ERROR")
        }
    }

    fun limpiarPantalla(){
        val produccion: Produccion = Produccion( null, "Nombre", "Director",
            null,
            null,
            "Género",
            "Pais",
            0.0
        )
        _state.value = _state.value?.copy(produccion = produccion)
    }

    fun esPelicula(isCheked: Boolean){
        val esPelicula = _state.value?.produccion?.esPelicula
        if(isCheked == true){
            _state.value = _state.value?.copy(isEnable = false)
        } else {
            _state.value = _state.value?.copy(isEnable = true)
        }
    }

    fun limpiarMensaje(){
        _state.value = _state.value?.copy(mensaje = null)
    }
    fun siguienteProduccion(){
        val indice = _state.value?.indiceProduccion ?: 0
        val nuevoIndice = if (indice == sizeProduccionesUseCase()){
           0
        } else {
           indice +1
        }
        val produccion = getProduccionUseCase.invoke(nuevoIndice)
        _state.value = _state.value?.copy(produccion = produccion, indiceProduccion = nuevoIndice)
    }
    fun anteriorProduccion(){
        val indice = _state.value?.indiceProduccion ?: 0

        val nuevoIndice :Int = if (indice == 0){
            sizeProduccionesUseCase() - 1
        } else {
            indice -1
        }

        val produccion = getProduccionUseCase.invoke(nuevoIndice)
        _state.value = _state.value?.copy(produccion = produccion, indiceProduccion = nuevoIndice)

    }
    class MainViewModelFactory(
        private val aniadirProduccionUseCase: AnadirProduccionUseCase,
        private val getProduccionUseCase: GetProduccionUseCase,
        private val borrarProduccionUseCase: BorrarProduccionUseCase,
        private val sizeProduccionesUseCase: SizeProduccionesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(aniadirProduccionUseCase, getProduccionUseCase, borrarProduccionUseCase, sizeProduccionesUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}