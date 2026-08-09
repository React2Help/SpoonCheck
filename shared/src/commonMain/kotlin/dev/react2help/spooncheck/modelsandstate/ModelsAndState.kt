package dev.react2help.spooncheck.modelsandstate

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Instant

data class DashboardUIState(
    val total_spoons: Int,
    val consumed_spoons: Int,
    val user_name: String,
    val num_checkins: Int,
    val num_restdays: Int,
    val num_notifications: Int // meant for the badge on the top right Profile icon on the Dashboard screen
)
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
    val priority: Priority = Priority.medium,
    val DueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val Spoons: Int = 0,
    val NotificationsOn: Boolean = false,
    val IsRecurring: Boolean = false,
    val Category: String = "Hygiene",
    val StartDate: LocalDate,
    val errorMessage: String = ""
)
sealed interface TaskCreationActions{ // defining types for our actions, so the callback functions
    // must satisfy this contract
    data object OnDelete: TaskCreationActions // since these actions don't need arguments, they are
    // specified as objects
    data object OnSave: TaskCreationActions
    data class OnTitleChanged( // these actions need arguments, so they are specified as classes
        val Title: String
    ): TaskCreationActions
    data class OnDescriptionChanged(
        val Description : String
    ): TaskCreationActions
    data class OnNotificationsChanged(
        val ShouldNotify: Boolean
    ): TaskCreationActions
    data class OnStartDateChanged(
        val StartDate: Instant
    ): TaskCreationActions
    data class OnDueDateChanged(
        val DueDate: LocalDate
    ): TaskCreationActions
    data class OnSpoonSelectedChanged(
        val IndexOfSelectedSpoon: Int
    ): TaskCreationActions
    data class OnCategoryChanged(
        val Category:String
    ): TaskCreationActions
    data class OnPriorityChanged(
        val Priority: Priority
    ): TaskCreationActions

}

data class Task(
    val title: String,
    val description: String,
    val spoons: Int,
    val priority: Priority,
    val due_date: LocalDate,
    val due_time: LocalTime
)

enum class Priority {
    low,
    medium,
    high,
    critical
}