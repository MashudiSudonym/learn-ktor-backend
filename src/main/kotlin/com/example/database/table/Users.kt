package com.example.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable() {
    val name = varchar("name", 128)
    val dateCreated = varchar("date_created", 10)
    val username = varchar("username", 128)
    val password = varchar("password", 128)
}