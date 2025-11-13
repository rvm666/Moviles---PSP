package com.example.gestionproduccionesnavegacion.data.repository

import com.example.gestionproduccionesnavegacion.data.local.dao.ProduccionesDao
import com.example.gestionproduccionesnavegacion.data.local.entity.toProduccion
import com.example.gestionproduccionesnavegacion.data.local.entity.toProduccionEntity
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import javax.inject.Inject

class RepositoryProducciones @Inject constructor(private val produccionesDao: ProduccionesDao) {

    suspend fun getAllProducciones(): List<Produccion> {
        return produccionesDao.getAllProducciones().map { it.toProduccion() }
    }


    suspend fun getNumTotalProducciones(): Int {
        return produccionesDao.getNumTotalProducciones()
    }

    suspend fun getProduccionMasVista(): String?{
        return produccionesDao.getProduccionMasVista()?.nombre
    }

    suspend fun getGeneroMasPopular(): String?{
        return produccionesDao.getGeneroMasPopular()?.genero
    }

    suspend fun getProduccionById(produccionId: Int): Produccion {
        val produccionEntity = produccionesDao.getProduccionById(produccionId)
        return produccionEntity.toProduccion()
    }


    suspend fun insertProduccion(produccion: Produccion) {
        produccionesDao.insertProduccion(produccion.toProduccionEntity())
    }
}