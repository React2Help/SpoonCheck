package dev.react2help.spooncheck

import android.app.Application
import dev.react2help.spooncheck.database.getRoomDatabase
import dev.react2help.spooncheck.repositories.PersistentTaskRepository
import dev.react2help.spooncheck.repositories.TaskRepository
class SpoonCheckApplication: Application() {
    val taskRepository: TaskRepository by lazy {
        val database = getRoomDatabase(getDatabaseBuilder(applicationContext))
        PersistentTaskRepository(
            taskDao = database.getDao()
        )
    }
}
