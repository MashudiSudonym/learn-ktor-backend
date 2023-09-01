package com.example.users.dao

import com.example.users.model.User
import com.example.users.model.UserRequest

interface UsersDAOFacade {
    suspend fun createUser(userRequest: UserRequest): User?
    suspend fun getUsers(): List<User>
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?
    suspend fun existById(id: String): Boolean
    suspend fun existByUsername(username: String): Boolean
}