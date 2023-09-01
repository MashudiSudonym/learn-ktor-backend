package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureDI()
    configureDatabase(environment.config)
    configureAuthentication(environment.config)
    configureStatusPages()
    configureResource()
    configureSerialization()
    configureRouting(environment.config)
}
