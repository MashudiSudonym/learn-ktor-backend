package com.example.games.presenter

import com.example.games.model.GameRequest
import com.example.games.repository.GamesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.gamesRouting() {
    val gamesRepository by inject<GamesRepository>()

    get("/games") {
        val games = gamesRepository.getGames()

        if (games.isNotEmpty()) {
            call.respond(games)
        } else {
            call.respondText(text = "There are no games in our shop yet...", status = HttpStatusCode.OK)
        }
    }
    post("/games") {
        val gameRequest = call.receive<GameRequest>()
        val game = gamesRepository.addGame(gameRequest)
        call.respond(status = HttpStatusCode.Created, message = game)
    }
    get("{id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            text = "Id is required",
            status = HttpStatusCode.BadRequest
        )
        val game = gamesRepository.getGame(id) ?: return@get call.respondText(
            text = "Game with $id does not exist",
            status = HttpStatusCode.NotFound
        )

        call.respond(game)
    }
}