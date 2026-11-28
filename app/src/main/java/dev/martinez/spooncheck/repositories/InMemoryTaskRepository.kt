package dev.martinez.spooncheck.repositories

import dev.martinez.spooncheck.DeletionError
import dev.martinez.spooncheck.DeletionException
import dev.martinez.spooncheck.SaveError
import dev.martinez.spooncheck.SaveException
import dev.martinez.spooncheck.database.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskRepository(initialTasks: List<Task> = emptyList()) : TaskRepository {
    private val _tasks = MutableStateFlow(initialTasks)

    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    @Suppress(
        "ReturnCount",
    ) // rule: Function save has 3 return statements which exceeds the limit of 2. [ReturnCount]
    override suspend fun save(
        task: Task,
    ): Result<Unit> { // todo modify this function to surface errors in that
        // lambda and return them to the caller. Define an enum of errors.
        if (task.title.isBlank()) {
            return Result.failure(SaveException(SaveError.INVALID_TASK))
        }

        return Result.success(Unit)
    }

    override suspend fun deleteById(taskId: Long): Result<Unit> {
        // delete a task if it exists. Else, return an error.
        if (_tasks.value.any { it.id == taskId })
            _tasks.update { currentTasks -> currentTasks.filterNot { it.id == taskId } }

        return Result.failure(DeletionException(DeletionError.NOT_FOUND))
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

    override suspend fun getTaskById(taskId: Long): Task {
        TODO("Not yet implemented")
    }
}
