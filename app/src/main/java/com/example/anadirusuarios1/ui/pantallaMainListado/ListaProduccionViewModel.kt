package com.example.anadirusuarios1.ui.pantallaMainListado

import androidx.lifecycle.ViewModel
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase

class ListaProduccionViewModel(
    private val repositorio: RepositorioProducciones,
    private val getProduccionesUseCase : GetProduccionUseCase = GetProduccionUseCase(repositorio)
) : ViewModel(){

}