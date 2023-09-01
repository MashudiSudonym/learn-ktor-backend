package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.users.repository.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureAuthentication(applicationConfig: ApplicationConfig) {
    val usersRepository by inject<UsersRepository>()

    val secret = applicationConfig.property("jwt.secret").getString()
    val issuer = applicationConfig.property("jwt.issuer").getString()
    val audience = applicationConfig.property("jwt.audience").getString()
    val myRealm = applicationConfig.property("jwt.realm").getString()

    install(Authentication) {
        jwt("jwt-auth") {
            realm = myRealm

            verifier {
                JWT.require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
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