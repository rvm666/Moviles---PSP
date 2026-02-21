package com.example.navegacioncifradopsp.data.model.authentication

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accesToken")
    val token: String,

    @SerializedName("refreshToken")
    val refreshToken: String
)
