package com.example.navegacioncifradopsp.domain.model

data class Usuario(
    val nombre: String = "",
    val usuario: String = "",
    val email: String = "",
    val isAdmin: Boolean  = false,
    val password: String = ""
)
