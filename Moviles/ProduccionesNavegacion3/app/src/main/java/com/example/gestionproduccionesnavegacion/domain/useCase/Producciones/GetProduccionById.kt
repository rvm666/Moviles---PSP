package com.example.gestionproduccionesnavegacion.domain.useCase.Producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetProduccionById @Inject constructor(private val repostiroy: RepositoryProducciones) {

    operator suspend fun invoke(produccionId: Int) =
        repostiroy.getProduccionById(produccionId)
}