package dev.react2help.spooncheck.repositories

import androidx.sqlite.SQLiteException
import dev.react2help.spooncheck.database.Event
import dev.react2help.spooncheck.database.EventDao
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class PersistentEventRepository(private val eventDao: EventDao) : EventRepository {
    override val tasks: Flow<List<Event>>
        get() = eventDao.getAllEventsAsFlow()

    override suspend fun save(event: Event): Result<Unit> {
        try {
            eventDao.insert(event)
        } catch (e: CancellationException) {
            throw e
        } catch (e: SQLiteException) {
            println(e)
        }
        return Result.success(Unit)
    }

    override suspend fun deleteById(taskId: Long): Result<Unit> {
        eventDao.deleteOne(taskId)
        return Result.success(Unit)
    }

    override suspend fun setCompleted(taskId: Long, isDone: Boolean) {
        eventDao.setCompleted(taskId, isDone)
    }
}
