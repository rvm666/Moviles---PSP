package com.example.composeapp.domain.usecase

import com.example.composeapp.data.RepositorioProducciones
import com.example.composeapp.domain.model.Produccion
import javax.inject.Inject

class ActualizarProduccionUseCase @Inject constructor(private val producciones : RepositorioProducciones) {

    operator fun invoke(id:Int, produccion: Produccion){
        producciones.actualizarProduccion(id, produccion)
    }
}