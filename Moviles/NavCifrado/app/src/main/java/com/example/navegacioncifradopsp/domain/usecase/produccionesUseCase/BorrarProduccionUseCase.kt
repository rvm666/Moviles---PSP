package com.example.navegacioncifradopsp.domain.usecase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.repository.ProduccionesRepository
import com.example.navegacioncifradopsp.domain.model.Produccion
import javax.inject.Inject

class BorrarProduccionUseCase @Inject constructor(private val repositorio: ProduccionesRepository) {

    suspend operator fun invoke(produccion: Produccion) : NetworkResult<Boolean> {
        return repositorio.borrarProduccion(produccion)
    }
}