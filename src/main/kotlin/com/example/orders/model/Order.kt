package com.example.orders.model

import com.example.games.model.Game
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val orderDate: String,
    val games: List<Game>,
    val price: Float,
    val address: String,
)
