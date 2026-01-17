package com.example.composeapp.domain.usecase

import com.example.composeapp.data.RepositorioProducciones
import com.example.composeapp.domain.model.Produccion
import javax.inject.Inject

class GetProduccionUseCase @Inject constructor(private val repositorio: RepositorioProducciones) {

    operator fun invoke(id: Int): Produccion {
        return repositorio.getProduccion(id)
    }
}