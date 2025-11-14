package com.example.gestionproduccionesnavegacion.ui.ListaUsuarios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios.GetAllUsuariosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ListaUsuariosViewModel @Inject constructor(
    private val getAllUsuariosUseCase: GetAllUsuariosUseCase
): ViewModel() {

    var state : MutableLiveData<ListaUsuariosState> = MutableLiveData()
        private set
    init{
        cargarUsuarios()
    }

    fun cargarUsuarios(){
        viewModelScope.launch {
            val usuarios = getAllUsuariosUseCase()
            state.value = ListaUsuariosState(usuarios = usuarios)
        }
    }


}