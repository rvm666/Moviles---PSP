package com.example.anadirusuarios1.domain.useCase

import com.example.anadirusuarios1.data.RepositorioProducciones

class SizeProduccionesUseCase(private val producciones : RepositorioProducciones) {

    operator fun invoke () : Int{
        return producciones.size()
    }
}