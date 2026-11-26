package dev.martinez.spooncheck

import android.app.Application
import dev.martinez.spooncheck.database.getRoomDatabase
import dev.martinez.spooncheck.getDatabaseBuilder
import dev.martinez.spooncheck.repositories.EventRepository
import dev.martinez.spooncheck.repositories.PersistentEventRepository
import dev.martinez.spooncheck.repositories.PersistentTaskRepository
import dev.martinez.spooncheck.repositories.TaskRepository
import kotlin.getValue
class SpoonCheckApplication: Application() {
    val taskRepository: TaskRepository by lazy {
        val database = getRoomDatabase(getDatabaseBuilder(applicationContext))
        PersistentTaskRepository(
            taskDao = database.getTaskDao()
        )
    }
    val eventRepository: EventRepository by lazy {
        val database = getRoomDatabase(getDatabaseBuilder(applicationContext))
        PersistentEventRepository(
            eventDao = database.getEventDao()
        )
    }

}