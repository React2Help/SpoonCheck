@file:Suppress("TopLevelPropertyNaming")

package dev.react2help.spooncheck.database

import androidx.room3.AutoMigration
import androidx.room3.ColumnTypeConverters
import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

private const val FIRST_VERSION = 1
private const val SECOND_VERSION = 2

@Database(
    entities = [Task::class, Event::class],
    version = SECOND_VERSION,
    autoMigrations = [AutoMigration(from = FIRST_VERSION, to = SECOND_VERSION)]
)
@ColumnTypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    abstract fun getEventDao(): EventDao
}

val MIGRATION_1_2 =
    object : Migration(1, 2) {
        override suspend fun migrate(connection: SQLiteConnection) {
            connection.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `events` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `title` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                `spoons` INTEGER NOT NULL,
                `priority` TEXT NOT NULL,
                `category` TEXT NOT NULL,
                `onDate` TEXT NOT NULL,
                `atTime` TEXT NOT NULL,
                `isDone` INTEGER NOT NULL
            )
            """
                    .trimIndent()
            )
        }
    }

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder.setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO).build()
}
