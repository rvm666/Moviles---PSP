package com.example.gestionproduccionesnavegacion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestionproduccionesnavegacion.data.local.entity.ProduccionEntity
import com.example.gestionproduccionesnavegacion.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProduccionesDao {

    data class NombreResult(val nombre: String)
    data class GeneroResult(val genero: String)


    @Query("SELECT * FROM producciones")
    suspend fun getAllProducciones(): List<ProduccionEntity>

    @Query("SELECT COUNT(*) FROM producciones")
    suspend fun getNumTotalProducciones(): Int

    @Query("""
    SELECT p.nombre 
    FROM producciones p
    INNER JOIN usuario_produccion_ref upr ON p.id = upr.produccion
    WHERE upr.vista = 1
    GROUP BY p.id, p.nombre
    ORDER BY COUNT(*) DESC
    LIMIT 1""")
    suspend fun getProduccionMasVista(): NombreResult?

    @Query("""
    SELECT p.genero
    FROM producciones p
    INNER JOIN usuario_produccion_ref upr ON p.id = upr.produccion
    WHERE upr.vista = 1
    GROUP BY p.genero
    ORDER BY COUNT(*) DESC
    LIMIT 1""")
    suspend fun getGeneroMasPopular(): GeneroResult?

    @Query("SELECT * FROM producciones WHERE id = :produccionId")
    suspend fun getProduccionById(produccionId: Int): ProduccionEntity

    @Insert
    suspend fun insertProduccion(produccion: ProduccionEntity)

}