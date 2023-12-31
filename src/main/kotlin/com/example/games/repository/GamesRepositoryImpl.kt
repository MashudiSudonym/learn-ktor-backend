package com.example.games.repository

import com.example.games.model.Game
import com.example.games.model.GameRequest
import java.util.*

class GamesRepositoryImpl : GamesRepository {
    private val games: MutableList<Game> = mutableListOf()

    override fun getGames(): List<Game> {
        return games
    }

    override fun addGame(gameRequest: GameRequest): Game {
        return Game(
            id = UUID.randomUUID().toString(),
            name = gameRequest.name,
            price = gameRequest.price.toFloat(),
            genre = gameRequest.genre,
        ).also { games.add(it) }
    }

    override fun getGame(id: String): Game? {
        return games.firstOrNull { it.id == id }
    }
}