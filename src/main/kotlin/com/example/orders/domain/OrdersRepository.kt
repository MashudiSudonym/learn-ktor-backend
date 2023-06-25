package com.example.orders.domain

import com.example.orders.data.Order
import com.example.orders.data.OrderRequest

interface OrdersRepository {
    fun getOrders(): List<Order>
    fun addOrder(orderRequest: OrderRequest): Order
    fun getOrder(orderId: String): Order?
    fun delete(orderId: String)
}