package com.example.users.repository

import com.example.users.model.User
import com.example.users.model.UserRequest

interface UsersRepository {
    suspend fun addUser(userRequest: UserRequest): User?
    suspend fun getUsers(): List<User>
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?
    suspend fun existById(id: String): Boolean
    suspend fun existByName(username: String): Boolean
}