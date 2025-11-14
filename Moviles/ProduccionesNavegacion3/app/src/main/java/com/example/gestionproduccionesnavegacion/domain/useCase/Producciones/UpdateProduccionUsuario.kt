package com.example.gestionproduccionesnavegacion.domain.useCase.Producciones

import com.example.gestionproduccionesnavegacion.data.local.entity.toProduccionEntity
import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import javax.inject.Inject

class UpdateProduccionUsuario @Inject constructor(private val repository: RepositoryProducciones) {

    operator suspend fun invoke(produccion: Produccion, idUsuario: Int): Boolean{
        return try {
            val actualizado = repository.updateProduccion(produccion)
            repository.updateUsuarioProduccionRef(idUsuario, produccion.id, produccion.vista == true)
            true
        } catch (e: Exception) {
            false
        }
    }

}