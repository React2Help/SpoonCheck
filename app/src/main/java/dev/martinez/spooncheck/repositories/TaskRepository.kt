package dev.martinez.spooncheck.repositories

import dev.martinez.spooncheck.database.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val tasks: Flow<List<Task>>

    suspend fun save(task: Task): Result<Unit>

    suspend fun deleteById(taskId: Long): Result<Unit>

    suspend fun setCompleted(taskId: Long, isDone: Boolean)

    suspend fun getTaskById(taskId: Long): Task
}
