package com.example.games.data

import kotlinx.serialization.Serializable

@Serializable
data class GameRequest(
    val name: String,
    val price: String,
    val genre: String,
)
