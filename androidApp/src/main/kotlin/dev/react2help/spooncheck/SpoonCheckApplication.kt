package dev.react2help.spooncheck

import android.app.Application
import dev.react2help.spooncheck.database.Task
import dev.react2help.spooncheck.database.getRoomDatabase
import dev.react2help.spooncheck.repositories.EventRepository
import dev.react2help.spooncheck.repositories.PersistentEventRepository
import dev.react2help.spooncheck.repositories.PersistentTaskRepository
import dev.react2help.spooncheck.repositories.TaskRepository
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
