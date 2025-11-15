package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class DeleteProduccion @Inject constructor(private val repository: RepositoryProducciones) {

    suspend operator fun invoke(produccionId: Int): Boolean{
        return repository.deleteProduccionById(produccionId)
    }
}