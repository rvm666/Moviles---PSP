package com.example.gestionproduccionesnavegacion.domain.useCase.Producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import javax.inject.Inject

class InsertarProduccion @Inject constructor(private val repository: RepositoryProducciones) {

    operator suspend fun invoke(produccion: Produccion){
        return repository.insertProduccion(produccion)
    }
}