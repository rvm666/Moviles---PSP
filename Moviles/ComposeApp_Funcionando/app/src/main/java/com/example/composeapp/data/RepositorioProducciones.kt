package com.example.composeapp.data

import com.example.composeapp.domain.model.Produccion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioProducciones @Inject constructor() {
    private val producciones = mutableListOf<Produccion>()

    fun getProduccion(id: Int): Produccion = producciones[id]

    fun actualizarProduccion(id: Int, produccion: Produccion) {
        producciones[id] = produccion
    }

    fun aniadirProduccion(produccion: Produccion): Boolean = producciones.add(produccion)

    fun borrarProduccion(produccion: Produccion): Boolean = producciones.remove(produccion)

    fun size(): Int = producciones.size
}
