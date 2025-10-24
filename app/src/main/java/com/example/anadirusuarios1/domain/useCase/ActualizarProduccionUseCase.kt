package com.example.anadirusuarios1.domain.useCase

import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.domain.model.Produccion

class ActualizarProduccionUseCase(private val producciones : RepositorioProducciones) {

    operator fun invoke(nombre: String, produccion: Produccion) : Boolean {
        return producciones.actualizarProduccion(nombre, produccion)
    }
}