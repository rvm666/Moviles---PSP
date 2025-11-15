package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import javax.inject.Inject

class GetProduccionesVistas  @Inject constructor(private val repostiroy: RepositoryProducciones) {

    suspend operator fun invoke(id: Int): List<Produccion>{
        return repostiroy.getProduccionesVistas(id)
    }
}