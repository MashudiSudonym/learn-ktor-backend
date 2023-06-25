package com.example.orders.presentation

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/orders")
class OrdersResources(val price: Float? = null) {
    @Serializable
    @Resource("{id}")
    class Id(val parent: OrdersResources = OrdersResources(), val id: String) {
        @Serializable
        @Resource("edit")
        class Edit(val parent: Id, val name: String)

        @Serializable
        @Resource("address")
        class Address(val parent: Id)
    }
}