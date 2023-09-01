package com.example.database

import com.example.database.table.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactoryImpl : DatabaseFactory {
    private fun createHikariDataSource(
        url: String,
        driver: String,
        username: String,
        password: String
    ): HikariDataSource {
        return HikariDataSource(HikariConfig().apply {
            driverClassName = driver
            jdbcUrl = url
            setUsername(username)
            setPassword(password)
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })
    }

    override fun create(applicationConfig: ApplicationConfig) {
        Database.connect(
            createHikariDataSource(
                url = applicationConfig.property("storage.jdbcURL").getString(),
                driver = applicationConfig.property("storage.driverClassName").getString(),
                username = applicationConfig.property("storage.user").getString(),
                password = applicationConfig.property("storage.password").getString(),
            )
        )
        SchemaDefinition.createSchema()
    }

    private object SchemaDefinition {
        fun createSchema() {
            transaction {
                SchemaUtils.drop(Users)

                SchemaUtils.create(Users)

                Users.insert {
                    it[name] = "Admin"
                    it[username] = "admin"
                    it[password] = "pass"
                    it[dateCreated] = Clock.System.now().toLocalDateTime(TimeZone.UTC).date.toString()
                }
            }
        }
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }