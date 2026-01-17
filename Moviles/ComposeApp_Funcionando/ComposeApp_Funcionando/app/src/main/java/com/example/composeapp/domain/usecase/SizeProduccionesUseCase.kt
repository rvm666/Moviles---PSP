package com.example.composeapp.domain.usecase

import com.example.composeapp.data.RepositorioProducciones
import javax.inject.Inject

class SizeProduccionesUseCase @Inject constructor(private val producciones : RepositorioProducciones) {

    operator fun invoke () : Int{
        return producciones.size()
    }
}