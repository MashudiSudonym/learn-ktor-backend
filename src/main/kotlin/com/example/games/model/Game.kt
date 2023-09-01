package com.example.games.model

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: String,
    val name: String,
    val price: Float,
    val genre: String,
)
