package com.example.orders.presentation

import com.example.orders.model.OrderRequest
import com.example.orders.repository.OrdersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.ordersRouting() {
    val ordersRepository by inject<OrdersRepository>()

    get<OrdersResources> { request ->
        val price = request.price ?: 0f
        val orders = ordersRepository.getOrders().filter { it.price > price }

        call.respond(orders)
    }

    get<OrdersResources.Id> { request ->
        val order = ordersRepository.getOrder(request.id)

        order?.let { call.respond(it) } ?: call.respondText(
            status = HttpStatusCode.BadRequest, text = "No such order! OrderId: ${request.id}"
        )
    }

    get<OrdersResources.Id.Edit> { request ->
        call.respondText(
            status = HttpStatusCode.BadRequest,
            text = request.name
        )
    }

    get<OrdersResources.Id.Address> { request ->
        val address = ordersRepository.getOrder(request.parent.id)?.address
        address?.let { call.respond(it) } ?: call.respondText("No such order! OrderId: ${request.parent.id}")
    }

    post<OrdersResources> {
        val orderRequest = call.receive<OrderRequest>()
        val newOrder = ordersRepository.addOrder(orderRequest)
        call.respond(newOrder)
    }

    delete<OrdersResources.Id> { request ->
        try {
            ordersRepository.delete(request.id)
            call.respond(status = HttpStatusCode.NoContent, message = "Order deleted")
        } catch (e: Exception) {
            call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "No such order! OrderId: ${request.id}"
            )
        }
    }
}