package dev.react2help.spooncheck

import androidx.room3.Room
import androidx.room3.RoomDatabase
import dev.react2help.spooncheck.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/app_database.db"
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory =
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSDocumentDirectory,
            create = false,
            appropriateForURL = null,
            error = null
        )
    return requireNotNull(documentDirectory?.path)
}
