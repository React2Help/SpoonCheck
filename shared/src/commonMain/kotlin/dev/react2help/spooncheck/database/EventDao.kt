package dev.react2help.spooncheck.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert suspend fun insert(event: Event)

    @Query("UPDATE events SET isDone = :isDone WHERE id = :eventId")
    suspend fun setCompleted(eventId: Long, isDone: Boolean)

    @Query("SELECT * FROM events") fun getAllEventsAsFlow(): Flow<List<Event>>

    @Query("DELETE FROM events WHERE id = :eventId") suspend fun deleteOne(eventId: Long)

    @Delete suspend fun delete(event: Event)
}
