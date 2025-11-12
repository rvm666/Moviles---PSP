package com.example.anadirusuarios1.domain.useCase

import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.model.Produccion

class GetProduccionUseCase(private val repositorio: RepositorioProducciones) {

    operator fun invoke(id: Int): Produccion {
        return repositorio.getProduccion(id)
    }
}