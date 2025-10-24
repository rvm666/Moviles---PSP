package com.example.anadirusuarios1.ui.pantallaMainListado

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetAllProduccionesUseCase
import com.example.anadirusuarios1.ui.common.UiEvent
import com.example.anadirusuarios1.ui.common.Constantes

class ListaProduccionViewModel(
    private val repositorio: RepositorioProducciones,
    private val getAllProduccionesUseCase: GetAllProduccionesUseCase = GetAllProduccionesUseCase(repositorio),
    private val borrarProduccionUseCase: BorrarProduccionUseCase = BorrarProduccionUseCase(repositorio),
) : ViewModel(){

    var state: MutableLiveData<ListaProduccionesState> = MutableLiveData()
        private set
    init {
        cargarProducciones()
    }

    fun cargarProducciones(){
        val producciones = getAllProduccionesUseCase()
        state.value = ListaProduccionesState(producciones = producciones)
    }

    fun borrar(produccion: Produccion){
        if(borrarProduccionUseCase.invoke(produccion)){
            state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.PRODUCCION_BORRADA))
            cargarProducciones()
        } else {
            state.value = state.value?.copy(uiEvent = UiEvent.ShowSnackbar(Constantes.ERROR_BORRAR_PRODUCCION))
        }
    }

    fun limpiarMensaje(){
        state.value = state.value?.copy(uiEvent = null)
    }


    class ListaProduccionViewModelFactory(
        private val repositorio: RepositorioProducciones,
        private val getAllProduccionesUseCase: GetAllProduccionesUseCase,
        private val borrarProduccionUseCase: BorrarProduccionUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListaProduccionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListaProduccionViewModel(repositorio,
                    getAllProduccionesUseCase, borrarProduccionUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}