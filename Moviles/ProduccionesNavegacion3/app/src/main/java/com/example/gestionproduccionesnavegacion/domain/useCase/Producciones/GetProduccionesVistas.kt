package com.example.gestionproduccionesnavegacion.domain.useCase.Producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetProduccionesVistas  @Inject constructor(private val repostiroy: RepositoryProducciones) {

    operator suspend fun invoke(id: Int) =
        repostiroy.getProduccionesVistas(id)
}