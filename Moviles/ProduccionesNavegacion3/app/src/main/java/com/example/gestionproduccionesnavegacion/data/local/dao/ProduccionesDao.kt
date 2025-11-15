package com.example.gestionproduccionesnavegacion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.gestionproduccionesnavegacion.data.local.entity.ProduccionEntity
import com.example.gestionproduccionesnavegacion.data.local.entity.UsuarioProduccionRef

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


    @Query("""
    SELECT p.*
    FROM producciones p
    INNER JOIN usuario_produccion_ref upr ON p.id = upr.produccion
    WHERE upr.usuario = :usuarioId AND upr.vista = 1
    """)
    suspend fun getProduccionesVistasByUsuarioId(usuarioId: Int): List<ProduccionEntity>


    @Query("""
    SELECT p.*
    FROM producciones p
    LEFT JOIN usuario_produccion_ref upr 
    ON p.id = upr.produccion AND upr.usuario = :usuarioId
    WHERE upr.vista IS NULL OR upr.vista = 0
    """)
    suspend fun getProduccionesPendientesByUsuarioId(usuarioId: Int): List<ProduccionEntity>

    @Query("DELETE FROM producciones WHERE id = :id")
    suspend fun deleteProduccionById(id: Int): Int

    @Query("SELECT * FROM producciones WHERE id = :produccionId")
    suspend fun getProduccionById(produccionId: Int): ProduccionEntity

    @Query("""
    UPDATE usuario_produccion_ref 
    SET vista = :vista, valoracion = :valoracion 
    WHERE usuario = :usuarioId AND produccion = :produccionId
    """)
    suspend fun updateUsuarioProduccion(usuarioId: Int, produccionId: Int, vista: Boolean, valoracion: Double?)


    @Query("""
        SELECT COUNT(*) 
    FROM producciones p
    JOIN usuario_produccion_ref upr
    ON upr.produccion = p.id
    WHERE upr.usuario = :usuarioId
    AND upr.vista = 1
    """)
    suspend fun getNumProduccionesVistasByUsuarioId(usuarioId: Int): Int

    @Query("""
    SELECT COUNT(*)
    FROM producciones p
    LEFT JOIN usuario_produccion_ref upr
    ON upr.produccion = p.id
    AND upr.usuario = :usuarioId
    WHERE upr.produccion IS NULL
    OR upr.vista = 0
    """)
    suspend fun getNumProduccionesPendientesByUsuarioId(usuarioId: Int): Int

    @Upsert
    suspend fun upsertUsuarioProduccion(ref: UsuarioProduccionRef)

    @Insert
    suspend fun insertProduccion(produccion: ProduccionEntity): Long

    @Update
    suspend fun updateProduccion(produccion: ProduccionEntity): Int




}