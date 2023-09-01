package com.example.orders.repository

import com.example.orders.model.Order
import com.example.orders.model.OrderRequest

interface OrdersRepository {
    fun getOrders(): List<Order>
    fun addOrder(orderRequest: OrderRequest): Order
    fun getOrder(orderId: String): Order?
    fun delete(orderId: String)
}