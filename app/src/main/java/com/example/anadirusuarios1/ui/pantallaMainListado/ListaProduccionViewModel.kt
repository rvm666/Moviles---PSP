package com.example.anadirusuarios1.ui.pantallaMainListado

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.ActualizarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetAllProduccionesUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.SizeProduccionesUseCase
import com.example.anadirusuarios1.ui.pantallaAniadir.MainViewModel

class ListaProduccionViewModel(
    private val repositorio: RepositorioProducciones,
    private val getProduccionesUseCase : GetProduccionUseCase,
    private val getAllProduccionesUseCase: GetAllProduccionesUseCase = GetAllProduccionesUseCase(repositorio)
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

    fun aniadirProduccion(){
        // Lógica para añadir producción
    }

    fun infoProduccion(produccion: Produccion){

    }

    class ListaProduccionViewModelFactory(
   //     private val aniadirProduccionUseCase: AnadirProduccionUseCase,
        private val repositorio: RepositorioProducciones,
        private val getProduccionUseCase: GetProduccionUseCase,
        private val getAllProduccionesUseCase: GetAllProduccionesUseCase
//        private val borrarProduccionUseCase: BorrarProduccionUseCase,
//        private val sizeProduccionesUseCase: SizeProduccionesUseCase,
//        private val actualizarProduccionUseCase: ActualizarProduccionUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListaProduccionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListaProduccionViewModel(repositorio, getProduccionUseCase, getAllProduccionesUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}