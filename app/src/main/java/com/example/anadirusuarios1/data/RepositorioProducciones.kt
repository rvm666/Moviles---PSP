package com.example.anadirusuarios1.data

import com.example.anadirusuarios1.domain.model.Produccion

import org.threeten.bp.LocalDate
object RepositorioProducciones {
    private val producciones = mutableListOf<Produccion>()

    init {
        producciones.add(Produccion(false, "Breaking Bad", " Bryan Cranston", 5, LocalDate.of(2008, 1, 20), "Drama", "Estados Unidos", 4.5))
        producciones.add(Produccion(false, "La Casa de Papel", "Álex Pina", 4, LocalDate.of(2023, 11, 20), "Acción", "España", 4.0))
        producciones.add(Produccion(true, "Pulp Fiction", "Quentin Tarantino", null, LocalDate.of(1998, 5, 15), "Drama", "Estados Unidos", 4.5))
        producciones.add(Produccion(true, "Requiem for a Dream", "Darren Aronofsky", null, LocalDate.of(2001, 3, 23), "Drama", "Estados Unidos", 5.0))
        producciones.add(Produccion(true, "Trainspotting", "Danny Boyle", null, LocalDate.of(1996, 9, 23), "Comedia", "Reino Unido", 4.5))
        producciones.add(Produccion(true, "La haine", "Mathieu Kassovitz", null, LocalDate.of(1996, 1, 30), "Drama", "Francia", 4.0))
        producciones.add(Produccion(true, "Scarface", "Brian De Palma", null, LocalDate.of(1984, 3, 12), "Drama", "Francia", 4.5))
        producciones.add(Produccion(true, "Amanece que no es poco", "José Luis Cuerda", null, LocalDate.of(1989, 1, 17), "Comedia", "España", 3.5))
        producciones.add(Produccion(true, "Kill Bill", "Quentin Tarantino", null, LocalDate.of(2003, 10, 10), "Acción / Aventuras", "Estados Unidos", 3.5))
        producciones.add(Produccion(true, "Big fish", "Tim Burton", null, LocalDate.of(2004, 3, 5), "Comedia", "Estados Unidos", 4.5))
        producciones.add(Produccion(false, "Euphoria", "Hunter Schafer", 2, LocalDate.of(2019, 10, 16), "Drama", "Estados Unidos", 5.0))
        producciones.add(Produccion(true, "Entre tinieblas", "Pedro Almodóvar", null, LocalDate.of(1983, 3, 10), "Comedia", "España", 4.5))
        producciones.add(Produccion(true, "El resplandor", "Stanley Kubrick", null, LocalDate.of(1980, 12, 19), "Terror", "Estados Unidos", 3.5))
        producciones.add(Produccion(true, "El señor de los anillos", "Peter Jackson", null, LocalDate.of(2001, 12, 19), "Acción / Aventuras", "Estados Unidos", 5.0))
        producciones.add(Produccion(true, "El club de la lucha", "David Fincher", null, LocalDate.of(1999, 11, 5), "Acción / Aventuras", "Estados Unidos", 3.5))
        producciones.add(Produccion(true, "La naranja mecanica", "Stanley Kubrick", null, LocalDate.of(1975, 6, 16), "Drama", "Estados Unidos", 3.5))
        producciones.add(Produccion(false, "Friends", " Marta Kauffman", 10, LocalDate.of(1994, 9, 22), "Comedia", "Estados Unidos", 4.5))
        producciones.add(Produccion(true, "La mesita del comedor", "Caye Casas", null, LocalDate.of(2022, 12, 1), "Drama", "España", 4.0))

    }


    fun actualizarProduccion(nombre:String, produccion: Produccion) : Boolean{
        val id = producciones.indexOfFirst { it.nombre == nombre}
        return if(id != -1){
            producciones[id] = produccion
            true
        } else {
            false
        }
    }

    fun aniadirProduccion(produccion: Produccion) = producciones.add(produccion)

    fun getAllProducciones(): List<Produccion> = producciones.toList()

    fun borrarProduccion(produccion: Produccion) = producciones.remove(produccion)

}