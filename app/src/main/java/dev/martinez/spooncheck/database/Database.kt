@file:Suppress("TopLevelPropertyNaming")

package dev.martinez.spooncheck.database

import androidx.room3.AutoMigration
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import dev.martinez.spooncheck.database.seeding.DaoSeedCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private const val FIRST_VERSION = 1
private const val SECOND_VERSION = 2

@Database(
    entities = [Task::class, Event::class],
    version = SECOND_VERSION,
    autoMigrations = [AutoMigration(from = FIRST_VERSION, to = SECOND_VERSION)]
)
@ColumnTypeConverters(
    LocalDateConverter::class,
    LocalTimeConverter::class,
    PriorityConverter::class,
    CategoryConverter::class
)
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



fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
): AppDatabase {
    lateinit var db: AppDatabase
    db =
        builder
            .setDriver(BundledSQLiteDriver())
            .addCallback(DaoSeedCallback({ db }, scope))
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    return db
}
