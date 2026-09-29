package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.SaveError
import dev.react2help.spooncheck.SaveException
import dev.react2help.spooncheck.modelsandstate.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.any
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskRepository : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun save(task: Task): Result<Unit> { // todo modify this function to surface errors in that
        // lambda and return them to the caller. Define an enum of errors.
        if(task.title.isBlank()){
            return Result.failure(SaveException(SaveError.INVALID_TASK))
        }
        if(_tasks.value.any { it -> it.id == task.id}){
            return Result.failure(SaveException(SaveError.DUPLICATE_TASK))
        }
        _tasks.update { currentTasks -> currentTasks + task }
        return Result.success(Unit)
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
