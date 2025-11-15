package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetProduccionesPendientes @Inject constructor(private val repostiroy: RepositoryProducciones){

    suspend operator fun invoke(id: Int) =
        repostiroy.getProduccionesPendientes(id)
}