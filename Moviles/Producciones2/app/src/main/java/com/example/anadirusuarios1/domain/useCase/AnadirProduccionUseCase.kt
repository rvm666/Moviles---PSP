package com.example.anadirusuarios1.domain.useCase

import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.model.Produccion

class AnadirProduccionUseCase(private val repositorio: RepositorioProducciones) {

    operator fun invoke(produccion: Produccion) : Boolean{
        return repositorio.aniadirProduccion(produccion)
    }
}