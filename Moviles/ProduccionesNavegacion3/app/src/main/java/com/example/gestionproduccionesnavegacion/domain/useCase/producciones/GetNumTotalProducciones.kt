package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetNumTotalProducciones @Inject constructor(private val repostiroy: RepositoryProducciones) {

    suspend operator fun invoke(): Int{
        return repostiroy.getNumTotalProducciones()
    }
}