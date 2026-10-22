package dev.react2help.spooncheck.repositories

import androidx.sqlite.SQLiteException
import dev.react2help.spooncheck.database.TaskDao
import dev.react2help.spooncheck.database.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
class PersistentTaskRepository(private val taskDao: TaskDao): TaskRepository {
    // utilizes the Room3 database.
    // todo properly inspect exceptions for errors
    override val tasks: Flow<List<Task>>
        get() = taskDao.getAllTasksAsFlow()

    override suspend fun save(task: Task): Result<Unit> {
        try {
            taskDao.insert(task)
        }catch (e: CancellationException){
            throw e // rethrow the coroutine's cancellation ??
        }catch (e: SQLiteException){
            println(e)
        }
        return Result.success(Unit)
    }

    override suspend fun deleteOne(taskId: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun setCompleted(taskId: Long, isDone: Boolean) {
        TODO("Not yet implemented")
    }
}