package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import javax.inject.Inject

class GetNumProduccionesVistas @Inject constructor(private val repository: RepositoryProducciones){

    suspend operator fun invoke(id: Int): Int {
        return repository.getNumProduccionesVistasByUsuario(id)
    }
}