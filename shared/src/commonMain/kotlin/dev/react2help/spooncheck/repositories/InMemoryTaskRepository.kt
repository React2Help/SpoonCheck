package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.modelsandstate.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskRepository(initialTasks: List<Task> = emptyList()) : TaskRepository {
    private val _tasks = MutableStateFlow(initialTasks)

    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun save(task: Task) {
        _tasks.update { currentTasks -> currentTasks + task }
    }

    override suspend fun setCompleted(taskId: Long, isDone: Boolean) {
        _tasks.update { currentTasks ->
            currentTasks.map { task ->
                if (task.id == taskId) {
                    task.copy(isDone = isDone)
                } else {
                    task
                }
            }
        }
    }
}
