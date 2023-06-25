package com.example.login.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.login.data.LoginRequest
import com.example.login.data.LoginResponse
import com.example.plugins.AUDIENCE
import com.example.plugins.ISSUER
import com.example.plugins.SECRET
import com.example.users.domain.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import org.koin.ktor.ext.inject
import java.util.*

fun Route.loginRouting() {
    val usersRepository by inject<UsersRepository>()

    route("/login") {
        post {
            val loginRequest = call.receive<LoginRequest>()

            usersRepository.getUserByUsernameAndPassword(loginRequest.username, loginRequest.password)
                ?.let {
                    val token = JWT.create()
                        .withAudience(AUDIENCE)
                        .withIssuer(ISSUER)
                        .withClaim("userId", it.id)
                        .withExpiresAt(Date(Clock.System.now().toEpochMilliseconds() + 60000))
                        .sign(Algorithm.HMAC256(SECRET))

                    call.respond(LoginResponse(token))
                } ?: call.respondText(status = HttpStatusCode.BadRequest, text = "Invalid login or password")
        }
    }
}