package com.example.anadirusuarios1.ui.pantallaAniadir

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.ActualizarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.SizeProduccionesUseCase
import com.example.anadirusuarios1.ui.common.UiEvent

class MainViewModel(
    private val aniadirProduccionUseCase: AnadirProduccionUseCase,
    private val getProduccionUseCase: GetProduccionUseCase,
    private val borrarProduccionUseCase: BorrarProduccionUseCase,
    private val sizeProduccionesUseCase: SizeProduccionesUseCase,
    private val actualizarProduccionUseCase: ActualizarProduccionUseCase
) : ViewModel() {

    var state : MutableLiveData<MainState> = MutableLiveData(MainState())
        private set



    fun clickBotonGuardar(produccion: Produccion){
        if(aniadirProduccionUseCase.invoke(produccion)) {
            state.value = state.value?.copy(
                produccion = produccion,
                uiEvent = UiEvent.ShowSnackbar("Producción guardada"))
        } else {
            state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar("ERROR AL GUARDAR"))
        }
    }

    fun clickBotonBorrar(produccion: Produccion){
        if(borrarProduccionUseCase.invoke(produccion)){
            state.value = state.value?.copy(mensaje = "Produccion borrada")
        } else {
            state.value = state.value?.copy(mensaje = "ERROR")
        }
    }
    fun actualizarProduccion(produccion: Produccion){
        val id = state.value.indiceProduccion
        actualizarProduccionUseCase(id, produccion)
        state.value = state.value?.copy(produccion = produccion)
    }
    fun limpiarPantalla(){
        val produccion: Produccion = Produccion( null, "Nombre", "Director",
            null,
            null,
            "Género",
            "Pais",
            0.0
        )
        state.value = state.value?.copy(produccion = produccion)
    }

    fun esPelicula(isCheked: Boolean){
        if(isCheked == true){
            state.value = state.value?.copy(isEnable = false)
        } else {
            state.value = state.value?.copy(isEnable = true)
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }

    fun siguienteProduccion(){
        val indice = state.value?.indiceProduccion ?: 0
        val nuevoIndice = if (indice >= sizeProduccionesUseCase() -1){
           0
        } else {
           indice +1
        }
        val produccion = getProduccionUseCase.invoke(nuevoIndice)
        state.value = state.value?.copy(produccion = produccion, indiceProduccion = nuevoIndice)
    }
    fun anteriorProduccion(){
        val indice = state.value?.indiceProduccion ?: 0

        val nuevoIndice :Int = if (indice == 0){
            sizeProduccionesUseCase() - 1
        } else {
            indice -1
        }

        val produccion = getProduccionUseCase.invoke(nuevoIndice)
        state.value = state.value?.copy(produccion = produccion, indiceProduccion = nuevoIndice)

    }


    class MainViewModelFactory(
        private val aniadirProduccionUseCase: AnadirProduccionUseCase,
        private val getProduccionUseCase: GetProduccionUseCase,
        private val borrarProduccionUseCase: BorrarProduccionUseCase,
        private val sizeProduccionesUseCase: SizeProduccionesUseCase,
        private val actualizarProduccionUseCase: ActualizarProduccionUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(aniadirProduccionUseCase, getProduccionUseCase, borrarProduccionUseCase, sizeProduccionesUseCase, actualizarProduccionUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}