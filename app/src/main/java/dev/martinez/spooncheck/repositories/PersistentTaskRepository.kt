package dev.martinez.spooncheck.repositories

import androidx.sqlite.SQLiteException
import dev.martinez.spooncheck.database.Task
import dev.martinez.spooncheck.database.TaskDao
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class PersistentTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    // utilizes the Room3 database.
    // todo properly inspect exceptions for errors
    override val tasks: Flow<List<Task>>
        get() = taskDao.getAllTasksAsFlow()

    override suspend fun save(task: Task): Result<Unit> {
        try {
            taskDao.insert(task)
        } catch (e: CancellationException) {
            throw e // rethrow the coroutine's cancellation ??
        } catch (e: SQLiteException) {
            println(e)
        }
        return Result.success(Unit)
    }

    override suspend fun deleteById(taskId: Long): Result<Unit> {
        taskDao.deleteOne(taskId)
        return Result.success(Unit)
    }

    override suspend fun setCompleted(taskId: Long, isDone: Boolean) {
        taskDao.setCompleted(taskId, isDone)
    }

    override suspend fun getTaskById(taskId: Long): Task {
        return taskDao.getById(taskId)
    }
}
