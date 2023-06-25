package com.example.games.data

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: String,
    val name: String,
    val price: Float,
    val genre: String,
)
