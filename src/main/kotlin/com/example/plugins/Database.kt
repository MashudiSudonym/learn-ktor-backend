package com.example.plugins

import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureDatabase() {
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.create()
}