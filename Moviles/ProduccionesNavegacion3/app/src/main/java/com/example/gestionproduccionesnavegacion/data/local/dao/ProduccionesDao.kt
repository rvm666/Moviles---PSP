package com.example.gestionproduccionesnavegacion.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.gestionproduccionesnavegacion.data.local.entity.ProduccionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProduccionesDao {


    @Query("SELECT * FROM producciones")
    suspend fun getAllProducciones(): List<ProduccionEntity>



}