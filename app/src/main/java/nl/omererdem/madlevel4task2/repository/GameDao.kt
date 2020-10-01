package nl.omererdem.madlevel4task2

import androidx.room.*
import nl.omererdem.madlevel4task2.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)
}