package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.database.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val tasks: Flow<List<Task>>

    suspend fun save(task: Task): Result<Unit>

    suspend fun deleteById(taskId: Long): Result<Unit>

    suspend fun setCompleted(taskId: Long, isDone: Boolean)
}
