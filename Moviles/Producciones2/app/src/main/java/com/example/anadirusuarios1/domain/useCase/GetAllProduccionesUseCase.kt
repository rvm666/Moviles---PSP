package com.example.anadirusuarios1.domain.useCase

import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.model.Produccion


class GetAllProduccionesUseCase(private val repositorio: RepositorioProducciones) {

    operator fun invoke(): List<Produccion> {
        return repositorio.getAllProducciones()
    }
}