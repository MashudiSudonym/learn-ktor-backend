package com.example.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)
