package com.example.gestionproduccionesnavegacion.data.repository

import com.example.gestionproduccionesnavegacion.data.local.dao.ProduccionesDao
import com.example.gestionproduccionesnavegacion.data.local.entity.toProduccion
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import jakarta.inject.Inject

class RepositoryProducciones @Inject constructor(private val produccionesDao: ProduccionesDao) {

    suspend fun getAllProducciones(): List<Produccion> {
        return produccionesDao.getAllProducciones().map { it.toProduccion() }
    }

}