package com.example.database

import com.example.database.table.Users
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
    private companion object {
        const val driverClassName = "org.h2.Driver"
        const val jdbcURL = "jdbc:h2:file:./build/db"
    }

    override fun create() {
        Database.connect(jdbcURL, driverClassName)
        SchemaDefinition.createSchema()
    }

    private object SchemaDefinition {
        fun createSchema() {
            transaction {
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