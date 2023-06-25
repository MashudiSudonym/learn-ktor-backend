package com.example.orders.data

import com.example.games.data.Game
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val orderDate: String,
    val games: List<Game>,
    val price: Float,
    val address: String,
)
