package com.example.login.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.login.model.LoginRequest
import com.example.login.model.LoginResponse
import com.example.users.repository.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import org.koin.ktor.ext.inject
import java.util.*

fun Route.loginRouting(applicationConfig: ApplicationConfig) {
    val usersRepository by inject<UsersRepository>()

    val secret = applicationConfig.property("jwt.secret").getString()
    val issuer = applicationConfig.property("jwt.issuer").getString()
    val audience = applicationConfig.property("jwt.audience").getString()

    route("/login") {
        post {
            val loginRequest = call.receive<LoginRequest>()

            usersRepository.getUserByUsernameAndPassword(loginRequest.username, loginRequest.password)
                ?.let {
                    val token = JWT.create()
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .withClaim("userId", it.id)
                        .withExpiresAt(Date(Clock.System.now().toEpochMilliseconds() + 60000))
                        .sign(Algorithm.HMAC256(secret))

                    call.respond(LoginResponse(token))
                } ?: call.respondText(status = HttpStatusCode.BadRequest, text = "Invalid login or password")
        }
    }
}