package com.example.navegacioncifradopsp.data.model.authentication

import com.google.gson.annotations.SerializedName;

data class Auth(
    @SerializedName("email_address")
    val email: String,
    val password: String
)
