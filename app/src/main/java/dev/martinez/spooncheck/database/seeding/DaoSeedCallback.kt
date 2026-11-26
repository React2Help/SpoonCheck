@file:Suppress("MagicNumber")

package dev.martinez.spooncheck.database.seeding

import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteConnection
import dev.martinez.spooncheck.database.AppDatabase
import dev.martinez.spooncheck.database.Task
import dev.martinez.spooncheck.generateRandomFutureDate
import dev.martinez.spooncheck.generateRandomFutureTime
import dev.martinez.spooncheck.modelsandstate.Category
import dev.martinez.spooncheck.modelsandstate.Priority
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DaoSeedCallback(
    private val databaseProvider: () -> AppDatabase,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {
    override suspend fun onCreate(connection: SQLiteConnection) {
        super.onCreate(connection)
        scope.launch {
            val dao = databaseProvider().getTaskDao()
            dao.insertAll(seed1(15))
        }
    }
}

private fun seed1(numRows: Int): List<Task> =
    List(numRows) {
        Task(
            title = generateRandomTitle(),
            description = generateRandomDescription(Random.nextInt(10, 35)),
            spoons = Random.nextInt(1, 10),
            priority = generateRandomPriority(),
            category = generateRandomCategoru(),
            dueDate = generateRandomFutureDate(),
            dueTime = generateRandomFutureTime()
        )
    }

private fun generateRandomTitle(): String {
    val words =
        listOf(
            "Review",
            "Update",
            "Finalize",
            "Schedule",
            "Deploy",
            "Audit",
            "Draft",
            "Refactor",
            "Organize",
            "Submit",
            "Verify",
            "Research",
            "Sync",
            "Clean",
            "Prepare",
            "Report",
            "Invoice",
            "Budget",
            "Meeting",
            "Roadmap",
            "Backup",
            "Proposal",
            "Sprint",
            "Backlog",
            "Follow-up"
        )
    return when (Random.nextInt(3)) {
        0 -> "The ${words.random()}"
        1 -> "Complete ${words.random()}"
        else -> "${words.random()} ${words.random()} ${words.random()}"
    }
}

private fun generateRandomDescription(length: Int): String {
    val words =
        listOf(
            "urgent",
            "detailed",
            "preliminary",
            "quarterly",
            "client-facing",
            "internal",
            "pending",
            "updated",
            "comprehensive",
            "critical",
            "stakeholders",
            "team",
            "database",
            "documentation",
            "feedback",
            "requirements",
            "timeline",
            "deliverables",
            "metrics",
            "guidelines",
            "assets",
            "workflow",
            "approval",
            "system",
            "milestone"
        )
    return List(length) { words.random() }.joinToString(" ")
}

private fun generateRandomPriority(): Priority {
    return Priority.entries[Random.nextInt(Priority.entries.size)]
}

private fun generateRandomCategoru(): Category {
    return Category.entries[Random.nextInt(Category.entries.size)]
}
