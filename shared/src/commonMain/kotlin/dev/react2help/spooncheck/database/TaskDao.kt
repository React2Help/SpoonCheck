package dev.react2help.spooncheck.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import dev.react2help.spooncheck.database.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks") fun getAllTasksAsFlow(): Flow<List<Task>>
    @Delete
    suspend fun delete(task: Task)
}
