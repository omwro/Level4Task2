package nl.omererdem.madlevel4task2.repository

import android.content.Context
import nl.omererdem.madlevel4task2.database.GameRoomDatabase
import nl.omererdem.madlevel4task2.model.Game

// getter and setters for the games database
class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun updateGame(game: Game) {
        gameDao.updateGame(game)
    }
}