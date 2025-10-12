package com.example.anadirusuarios1.data

import com.example.anadirusuarios1.domain.model.Produccion

object RepositorioProducciones {
    private val producciones = mutableListOf<Produccion>()

    fun getProduccion(id:Int): Produccion = producciones[id]

    fun actualizarProduccion(id:Int, produccion: Produccion) = producciones.set(id, produccion)

    fun aniadirProduccion(produccion: Produccion) = producciones.add(produccion)

    fun borrarProduccion(produccion: Produccion) = producciones.remove(produccion)

    fun size() : Int = producciones.size
}