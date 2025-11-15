package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import javax.inject.Inject

class InsertarProduccion @Inject constructor(private val repository: RepositoryProducciones) {

    suspend operator fun invoke(produccion: Produccion): Boolean {
        return repository.insertProduccion(produccion)
    }
}