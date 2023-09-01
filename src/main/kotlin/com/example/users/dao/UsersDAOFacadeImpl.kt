package com.example.users.dao

import com.example.database.dbQuery
import com.example.database.table.Users
import com.example.users.model.User
import com.example.users.model.UserRequest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.*
import java.util.*

class UsersDAOFacadeImpl : UsersDAOFacade {
    override suspend fun createUser(userRequest: UserRequest): User? {
        return dbQuery {
            Users.insert {
                it[name] = userRequest.name
                it[username] = userRequest.username
                it[password] = userRequest.password
                it[dateCreated] = Clock.System.now().toLocalDateTime(TimeZone.UTC).date.toString()
            }.resultedValues?.singleOrNull()?.toUser()
        }
    }

    override suspend fun getUsers(): List<User> {
        return dbQuery { Users.selectAll().map { it.toUser() } }
    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return dbQuery {
            Users.select { (Users.username eq username) and (Users.password eq password) }
                .limit(1)
                .firstOrNull()
                ?.toUser()
        }
    }

    override suspend fun existById(id: String): Boolean {
        return dbQuery {
            Users.select {
                Users.id eq UUID.fromString(id)
            }.limit(1).count() > 0
        }
    }

    override suspend fun existByUsername(username: String): Boolean {
        return dbQuery {
            Users.select { Users.username eq username }.limit(1).count() > 0
        }
    }

    private fun ResultRow.toUser(): User {
        return this.let {
            User(
                id = it[Users.id].toString(),
                name = it[Users.name],
                username = it[Users.username],
            )
        }
    }
}