package dev.react2help.spooncheck.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks") fun getAllTasksAsFlow(): Flow<List<Task>>

    @Query("DELETE FROM tasks WHERE id = :taskId") suspend fun deleteOne(taskId: Long)

    @Query("UPDATE tasks SET isDone = :isDone WHERE id = :taskId")
    suspend fun setCompleted(taskId: Long, isDone: Boolean)

    @Delete suspend fun delete(task: Task)
}
