package com.example.composeapp.domain.usecase

import com.example.composeapp.data.RepositorioProducciones
import com.example.composeapp.domain.model.Produccion
import javax.inject.Inject

class BorrarProduccionUseCase @Inject constructor(private val repositorio: RepositorioProducciones) {

    operator fun invoke(produccion: Produccion) : Boolean {
        return repositorio.borrarProduccion(produccion)
    }
}