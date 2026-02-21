package com.example.navegacioncifradopsp.domain.usecase.produccionesUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.repository.ProduccionesRepository
import com.example.navegacioncifradopsp.domain.model.Produccion
import javax.inject.Inject

class GetAllProduccionesUseCase @Inject constructor(private val producciones: ProduccionesRepository) {

    suspend operator fun invoke(name: String): NetworkResult<List<Produccion>> {
        return producciones.getAll(name)
    }
}