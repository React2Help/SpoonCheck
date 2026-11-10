package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.database.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    val tasks: Flow<List<Event>>

    suspend fun save(event: Event): Result<Unit>

    suspend fun deleteById(taskId: Long): Result<Unit>

    suspend fun setCompleted(taskId: Long, isDone: Boolean)
}
