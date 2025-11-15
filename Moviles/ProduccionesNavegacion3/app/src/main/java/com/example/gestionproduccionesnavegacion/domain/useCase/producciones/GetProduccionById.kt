package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetProduccionById @Inject constructor(private val repostiroy: RepositoryProducciones) {

    suspend operator fun invoke(produccionId: Int) =
        repostiroy.getProduccionById(produccionId)
}