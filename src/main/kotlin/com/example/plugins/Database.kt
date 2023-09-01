package com.example.plugins

import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.koin.ktor.ext.inject

fun Application.configureDatabase(applicationConfig: ApplicationConfig) {
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.create(applicationConfig)
}