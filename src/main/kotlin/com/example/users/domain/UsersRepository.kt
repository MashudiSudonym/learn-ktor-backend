package com.example.users.domain

import com.example.users.data.User
import com.example.users.data.UserRequest

interface UsersRepository {
    suspend fun addUser(userRequest: UserRequest): User?
    suspend fun getUsers(): List<User>
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?
    suspend fun existById(id: String): Boolean
    suspend fun existByName(username: String): Boolean
}