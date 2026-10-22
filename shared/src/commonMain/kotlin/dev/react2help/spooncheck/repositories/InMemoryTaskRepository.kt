package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.DeletionError
import dev.react2help.spooncheck.DeletionException
import dev.react2help.spooncheck.SaveError
import dev.react2help.spooncheck.SaveException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import dev.react2help.spooncheck.database.Task
class InMemoryTaskRepository : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()


    private var nextID: Long = 0

    @Suppress(
        "ReturnCount"
    ) // rule: Function save has 3 return statements which exceeds the limit of 2. [ReturnCount]
    override suspend fun save(
        task: Task
    ): Result<Unit> { // todo modify this function to surface errors in that
        // lambda and return them to the caller. Define an enum of errors.
        if (task.title.isBlank()) {
            return Result.failure(SaveException(SaveError.INVALID_TASK))
        }

        return Result.success(Unit)
    }

    override suspend fun deleteOne(taskId: Long): Result<Unit> {
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
}
