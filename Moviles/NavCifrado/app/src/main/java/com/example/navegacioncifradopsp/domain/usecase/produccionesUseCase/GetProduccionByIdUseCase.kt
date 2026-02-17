package com.example.navegacioncifradopsp.domain.usecase.produccionesUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.repository.ProduccionesRepository
import com.example.navegacioncifradopsp.domain.model.Produccion
import com.example.navegacioncifradopsp.domain.model.isValidId
import javax.inject.Inject

class GetProduccionByIdUseCase @Inject constructor(private val repository: ProduccionesRepository) {

    suspend operator fun invoke(id: Int): NetworkResult<Produccion> =
        isValidId(id)
            .then { repository.getProduccionById(id) }

}