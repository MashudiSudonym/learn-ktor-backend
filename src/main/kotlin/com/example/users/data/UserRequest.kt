package com.example.users.data

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String,
    val username: String,
    val password: String,
)
