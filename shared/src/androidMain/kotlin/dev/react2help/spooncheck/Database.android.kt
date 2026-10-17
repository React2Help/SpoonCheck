package dev.react2help.spooncheck

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import dev.react2help.spooncheck.database.AppDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
