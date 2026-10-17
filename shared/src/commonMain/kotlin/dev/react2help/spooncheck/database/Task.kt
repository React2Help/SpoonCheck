package dev.react2help.spooncheck.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val spoons: Int,
    val priority: Priority,
    val category: Category,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val isDone: Boolean = false
)
