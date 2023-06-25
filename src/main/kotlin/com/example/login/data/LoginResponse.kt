package com.example.login.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)
