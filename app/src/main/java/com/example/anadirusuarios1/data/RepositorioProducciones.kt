package com.example.anadirusuarios1.data

import com.example.anadirusuarios1.domain.model.Produccion

import org.threeten.bp.LocalDate
object RepositorioProducciones {
    private val producciones = mutableListOf<Produccion>()

    init {
        producciones.add(Produccion(false, "Breaking Bad", "Al pacino", 5, LocalDate.of(2013, 1, 15), "Drama", "Estados Unidos", 9.5))
        producciones.add(Produccion(false, "La Casa de Papel", "Álex Pina", 4, LocalDate.of(2023, 11, 20), "Acción", "España", 8.3))
        producciones.add(Produccion(true, "Pulp Fiction", "Quentin Tarantino", null, LocalDate.of(1998, 5, 15), "Drama", "Estados Unidos", 8.5))

    }

    fun getProduccion(id:Int): Produccion = producciones[id]

    fun actualizarProduccion(id:Int, produccion: Produccion) = producciones.set(id, produccion)

    fun aniadirProduccion(produccion: Produccion) = producciones.add(produccion)

    fun getAllProducciones(): List<Produccion> = producciones.toList()

    fun borrarProduccion(produccion: Produccion) = producciones.remove(produccion)

    fun size() : Int = producciones.size
}