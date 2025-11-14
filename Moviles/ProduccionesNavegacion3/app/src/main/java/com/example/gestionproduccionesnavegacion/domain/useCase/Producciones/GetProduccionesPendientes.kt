package com.example.gestionproduccionesnavegacion.domain.useCase.Producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetProduccionesPendientes @Inject constructor(private val repostiroy: RepositoryProducciones){

    operator suspend fun invoke(id: Int) =
        repostiroy.getProduccionesPendientes(id)
}