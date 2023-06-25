package com.example.users.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val username: String,
)
