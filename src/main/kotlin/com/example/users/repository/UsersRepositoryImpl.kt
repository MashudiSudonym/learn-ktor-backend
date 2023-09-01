package com.example.users.repository

import com.example.users.model.User
import com.example.users.model.UserRequest
import com.example.users.dao.UsersDAOFacade

class UsersRepositoryImpl(private val dao: UsersDAOFacade) : UsersRepository {
    override suspend fun addUser(userRequest: UserRequest): User? {
        return dao.createUser(userRequest)
    }

    override suspend fun getUsers(): List<User> {
        return dao.getUsers()
    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return dao.getUserByUsernameAndPassword(username, password)
    }

    override suspend fun existById(id: String): Boolean {
        return dao.existById(id)
    }

    override suspend fun existByName(username: String): Boolean {
        return dao.existByUsername(username)
    }
}