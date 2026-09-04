package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.modelsandstate.Task
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>>

    suspend fun save(task: Task)

    suspend fun setCompleted(taskId: Long, isDone: Boolean)
}
