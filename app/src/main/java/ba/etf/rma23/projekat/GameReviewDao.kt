package ba.etf.rma23.projekat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameReviewDao {

        @Query("SELECT * FROM gamereview")
        suspend fun getAll(): List<GameReview>
        @Insert
        suspend fun insertAll(vararg reviews: GameReview)

        @Query("SELECT MAX(id) + 1 FROM GameReview")
        suspend fun getNextId(): Int?

        @Query("UPDATE GameReview SET online = 1 WHERE id = :id")
        suspend fun setOnlineById(id: Long)

}