package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetProduccionMasVista @Inject constructor(private val repository: RepositoryProducciones) {

    suspend operator fun invoke(): String? {
        return repository.getProduccionMasVista()
    }
}