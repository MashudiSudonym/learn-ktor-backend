package com.example.database

import io.ktor.server.config.*

interface DatabaseFactory {
    fun create(applicationConfig: ApplicationConfig)
}