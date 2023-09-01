package com.example.users.presenter

import com.example.users.model.UserRequest
import com.example.users.repository.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.usersRouting() {
    val usersRepository by inject<UsersRepository>()

    authenticate("jwt-auth") {
        route("/users") {
            install(RequestValidation) {
                validate<UserRequest> { request ->
                    when {
                        request.password.isBlank() -> ValidationResult.Invalid("Password is required!")
                        request.username.isBlank() -> ValidationResult.Invalid("Username is required!")
                        request.password.length < 5 -> ValidationResult.Invalid("Password is too short!")
                        request.name.length < 5 -> ValidationResult.Invalid("Name is too short!")
                        request.username.length < 5 -> ValidationResult.Invalid("Username is too short!")
                        usersRepository.existByName(request.username) -> ValidationResult.Invalid("User already exists!")
                        else -> ValidationResult.Valid
                    }
                }
            }
            post {
                val request = call.receive<UserRequest>()
                val user = usersRepository.addUser(request)

                requireNotNull(user)

                call.respond(
                    status = HttpStatusCode.Created, message = user
                )
            }

            get {
                val users = usersRepository.getUsers()

                call.respond(
                    status = HttpStatusCode.OK, message = users
                )
            }
        }
    }
}