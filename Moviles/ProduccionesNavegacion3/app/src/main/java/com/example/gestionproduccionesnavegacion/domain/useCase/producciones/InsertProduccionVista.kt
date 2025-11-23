package com.example.gestionproduccionesnavegacion.domain.useCase.producciones

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryProducciones
import com.example.gestionproduccionesnavegacion.domain.model.UsuarioProduccionCrossRef
import javax.inject.Inject

class InsertProduccionVista @Inject constructor(private val repository: RepositoryProducciones) {

    suspend operator fun invoke(usuarioProduccion: UsuarioProduccionCrossRef): Boolean{
        return repository.insertarProduccionVista(usuarioProduccion)
    }
}