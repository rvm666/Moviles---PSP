package com.example.navegacioncifradopsp.domain.usecase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.repository.ProduccionesRepository
import com.example.navegacioncifradopsp.domain.model.Produccion
import javax.inject.Inject

class ActualizarProduccionUseCase @Inject constructor(private val producciones : ProduccionesRepository) {

    suspend operator fun invoke(id:Int, produccion: Produccion): NetworkResult<Boolean> {
        return producciones.actualizarProduccion(id, produccion)
    }
}