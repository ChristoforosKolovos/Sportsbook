package christoforos.database.event

import androidx.room.*

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(drink: EventEntity)

    @Query("SELECT * FROM eventEntity")
    suspend fun getEvents(): List<EventEntity>

    @Delete
    suspend fun deleteEvent(drink: EventEntity)

}