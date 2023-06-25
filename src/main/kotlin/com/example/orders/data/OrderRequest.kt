package com.example.orders.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    @SerialName("games")
    val gamesIds: List<String>,
    val address: String,
)
