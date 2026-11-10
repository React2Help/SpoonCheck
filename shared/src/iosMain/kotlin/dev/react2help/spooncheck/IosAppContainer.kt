package dev.react2help.spooncheck

import dev.react2help.spooncheck.database.getRoomDatabase
import dev.react2help.spooncheck.repositories.PersistentEventRepository
import dev.react2help.spooncheck.repositories.PersistentTaskRepository

object IosAppContainer {
    val taskRepository by lazy {
        val database = getRoomDatabase(getDatabaseBuilder())
        PersistentTaskRepository(taskDao = database.getTaskDao())
    }
    val eventRepository by lazy {
        val database = getRoomDatabase(getDatabaseBuilder())
        PersistentEventRepository(eventDao = database.getEventDao())
    }
}
