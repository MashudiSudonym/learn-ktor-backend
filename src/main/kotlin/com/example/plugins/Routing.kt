package com.example.plugins

import com.example.games.presenter.gamesRouting
import com.example.login.presentation.loginRouting
import com.example.orders.presentation.ordersRouting
import com.example.users.presenter.usersRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        gamesRouting()
        ordersRouting()
        usersRouting()
        loginRouting()
    }
}
