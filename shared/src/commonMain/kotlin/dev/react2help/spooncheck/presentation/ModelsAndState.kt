package dev.react2help.spooncheck.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

data class TaskListUIState( // what the screen displays
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null
)
data class TaskCreationUIState(
    val isLoading: Boolean = false, // tracks if this UI is loading or not
    val isListening: Boolean = false, // boolean for tracking UI state for if the microphone is
    // listening for Voice input. Pressing the microphone button fires off a "listening for audio"
    // event which then mutates this variable to true. Other UI elements change their behavior based
    // on this variable to signal the microphone is listening.
    // ---


    // fields for the data in the form
    val Title: String = "",
    val Description: String = "",
    val priority: Priority = Priority.low,
    val DueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val Spoons: Int = 0,
    val errorMessage: String = ""
)
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