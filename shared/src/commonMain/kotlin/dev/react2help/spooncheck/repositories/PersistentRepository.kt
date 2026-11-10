package dev.react2help.spooncheck.repositories

import kotlinx.coroutines.flow.Flow

interface PersistentRepository<T> {
    val tasks: Flow<List<T>>

    suspend fun save(item: T): Result<Unit>

    suspend fun deleteById(taskId: Long): Result<Unit>

    suspend fun setCompleted(taskId: Long, isDone: Boolean)
}
