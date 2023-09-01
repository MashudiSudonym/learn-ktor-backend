package com.example.games.repository

import com.example.games.model.Game
import com.example.games.model.GameRequest

interface GamesRepository {
    fun getGames(): List<Game>
    fun addGame(gameRequest: GameRequest): Game
    fun getGame(id: String): Game?
}