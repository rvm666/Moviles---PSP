package com.example.navegacioncifradopsp.ui.navigation

import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
object Login

@Serializable
data class ProduccionDetail(val name: String)

@Serializable
object Register

@Serializable
object ListaProducciones

@Serializable
data class Perfil(val username: String)


