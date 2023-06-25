package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.users.domain.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

const val SECRET = "SECRETT"
const val AUDIENCE = "http://0.0.0.0:8080"
const val ISSUER = "http://0.0.0.0:8080/login"

fun Application.configureAuthentication() {
    val usersRepository by inject<UsersRepository>()

    install(Authentication) {
        jwt("jwt-auth") {
            realm = "GameShopAccess"

            verifier {
                JWT.require(Algorithm.HMAC256(SECRET))
                    .withAudience(AUDIENCE)
                    .withIssuer(ISSUER)
                    .build()
            }

            validate { jwtCredential ->
                jwtCredential.payload.claims["userId"]?.asString()?.let {
                    if (usersRepository.existById(it)) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }

            challenge { _, _ ->
                call.respond(status = HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}