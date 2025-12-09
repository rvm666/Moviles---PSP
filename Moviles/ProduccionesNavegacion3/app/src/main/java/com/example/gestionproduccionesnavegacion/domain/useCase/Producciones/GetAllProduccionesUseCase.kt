package com.example.gestionproduccionesnavegacion.domain.useCase.Producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import jakarta.inject.Inject

class GetAllProduccionesUseCase @Inject constructor(private val repository: RepositoryProducciones) {

    operator suspend fun invoke(): List<Produccion>{
       return repository.getAllProducciones()
    }

}