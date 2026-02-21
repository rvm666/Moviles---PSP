package com.example.navegacioncifradopsp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


sealed class Routes:NavKey {
    @Serializable
    data object Login : Routes()

    @Serializable
    data object Register : Routes()

    @Serializable
    data object ListaProducciones : Routes()
    @Serializable data object Secretos : Routes()
    @Serializable data object Perfil : Routes()

    @Serializable
    data class ProduccionDetail(val id: Int): Routes()

    @Serializable data class SecretoDetail(val id: Int) : Routes()
    @Serializable data object SecretosRecibidos : Routes()

}


