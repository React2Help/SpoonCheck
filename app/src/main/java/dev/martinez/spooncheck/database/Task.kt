package dev.martinez.spooncheck.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.martinez.spooncheck.modelsandstate.Category
import dev.martinez.spooncheck.modelsandstate.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String,
    val spoons: Int,
    val priority: Priority,
    val category: Category,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val isDone: Boolean = false,
) : SuperOfAllEntities
