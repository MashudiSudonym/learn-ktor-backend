package com.example.orders.domain

import com.example.games.data.Game
import com.example.games.domain.GamesRepository
import com.example.orders.data.Order
import com.example.orders.data.OrderRequest
import kotlinx.datetime.Clock
import java.util.*

class OrdersRepositoryImpl(private val gamesRepository: GamesRepository) : OrdersRepository {
    private val orders: MutableList<Order> = mutableListOf()

    override fun getOrders(): List<Order> {
        return orders
    }

    override fun addOrder(orderRequest: OrderRequest): Order {
        return orderRequest.toOrder() { gamesRepository.getGame(it) }
    }

    override fun getOrder(orderId: String): Order? {
        return orders.firstOrNull { it.id == orderId }
    }

    override fun delete(orderId: String) {
        val order = orders.first { it.id == orderId }
        orders.remove(order)
    }

    private fun OrderRequest.toOrder(gamesProvider: (String) -> Game?): Order {
        val games = this.gamesIds.map { requireNotNull(gamesProvider(it)) }

        return Order(
            id = UUID.randomUUID().toString(),
            orderDate = Clock.System.now().epochSeconds.toString(),
            games = games,
            price = games.map { it.price }.sum(),
            address = this.address
        ).also { orders.add(it) }
    }
}