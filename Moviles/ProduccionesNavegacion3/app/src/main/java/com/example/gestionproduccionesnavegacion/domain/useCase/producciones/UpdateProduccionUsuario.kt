package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import javax.inject.Inject

class UpdateProduccionUsuario @Inject constructor(private val repository: RepositoryProducciones) {

    suspend operator fun invoke(produccion: Produccion, idUsuario: Int): Boolean{
        return runCatching {
            repository.updateUsuarioProduccionRef(idUsuario, produccion.id, produccion.vista == true, produccion.valoracion)
        }.isSuccess
    }

}