package com.example.games.domain

import com.example.games.data.Game
import com.example.games.data.GameRequest

interface GamesRepository {
    fun getGames(): List<Game>
    fun addGame(gameRequest: GameRequest): Game
    fun getGame(id: String): Game?
}