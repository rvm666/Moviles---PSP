package com.example.navegacioncifradopsp.domain.model

import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult

fun isValidPage(page: Int): NetworkResult<Boolean> {
    return  if (page < 0 || page > 500)  NetworkResult.Error(Constantes.PAGINA_NEGATIVA) else  NetworkResult.Success(true)
}

fun isValidId(id: Int): NetworkResult<Boolean> {
    return  if (id < 0)  NetworkResult.Error(Constantes.ID_NEGATIVO) else  NetworkResult.Success(true)
}