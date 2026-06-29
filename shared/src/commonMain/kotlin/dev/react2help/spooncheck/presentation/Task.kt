package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.MutatePriority
import kotlinx.datetime.LocalDate

data class Task(
    val title: String,
    val description: String,
    val spoons: Int,
    val priority: Priority,
    val due_date: LocalDate
)

enum class Priority {
    low,
    medium,
    high,
    critical
}
