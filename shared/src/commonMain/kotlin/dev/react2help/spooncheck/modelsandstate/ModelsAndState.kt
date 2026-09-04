package dev.react2help.spooncheck.modelsandstate

import dev.react2help.spooncheck.utils.plusHoursSimple
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

data class DashboardUIState(
    val total_spoons: Int,
    val consumed_spoons: Int,
    val user_name: String,
    val num_checkins: Int,
    val num_restdays: Int,
    val num_notifications:
        Int // meant for the badge on the top right Profile icon on the Dashboard screen
)

data class TaskListUIState( // what the screen displays
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val listFilterOption: TaskListFilterOptions = TaskListFilterOptions.ALL_TASKS,
    val spoons: Int = 1,
    // todo add a field for the Icon of the Account button. Pending learning how to do this.
    val errorMessage: String? = null
)

sealed interface TaskListActions {
    data class onFilterOptionChange(val filterOption: TaskListFilterOptions) : TaskListActions

    data class CompletionChanged(
        val taskId: Long,
        val isDone: Boolean,
    ) : TaskListActions
    // don't think I need to hoist the state for the list.
    // Until we implement more complex functionality.
}

enum class TaskListFilterOptions {
    ALL_TASKS,
    Todo,
    Done
}

const val DefaultAdditionToDueTimeField = 8

data class TaskCreationUIState(
    val isLoading: Boolean = false, // tracks if this UI is loading or not
    val isListening: Boolean = false, // boolean for tracking UI state for if the microphone is
    // listening for Voice input. Pressing the microphone button fires off a "listening for audio"
    // event which then mutates this variable to true. Other UI elements change their behavior based
    // on this variable to signal the microphone is listening.
    // ---

    // fields for the data in the form
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.medium,
    val dueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val spoons: Int = 0,
    val notificationsOn: Boolean = false,
    val isRecurring: Boolean = false,
    val category: Category = Category.NONE,
    // should startdate be nullable?
    val startDate: LocalTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
    val errorMessage: String = "",
    val dueTime: LocalTime =
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .time
            .plusHoursSimple(DefaultAdditionToDueTimeField),
    val wasSaved: Boolean = false
)

sealed interface TaskCreationActions { // defining types for our actions, so the callback functions
    // must satisfy this contract
    data object Cancel : TaskCreationActions // since these actions don't need arguments, they are
    // specified as objects
    data object Save : TaskCreationActions

    data class OnTitleChanged( // these actions need arguments, so they are specified as classes
        val title: String
    ) : TaskCreationActions

    data class OnDescriptionChanged(val description: String) : TaskCreationActions

    data class OnNotificationsChanged(val shouldNotify: Boolean) : TaskCreationActions

    data class OnDueTimeChanged(val dueTime: LocalTime) : TaskCreationActions

    data class OnDueDateChanged(val dueDate: LocalDate) : TaskCreationActions

    data class OnSpoonSelectedChanged(val spoons: Int) : TaskCreationActions

    data class OnCategoryChanged(val category: Category) : TaskCreationActions

    data class OnPriorityChanged(val priority: Priority) : TaskCreationActions

    data class OnRecursChanged(val recurs: Boolean) : TaskCreationActions
}

data class Task( // todo add other fields
    /*
       date and time are nullable because I believe the user should be able to create tasks with no
       due date or time.

       spoons default value is zero because I believe we should always encourage the user to think
       about how much effort a task consumes.


    */
    val id: Long,
    val title: String,
    val description: String,
    val spoons: Int = 0,
    val priority: Priority,
    val category: Category,
    val dueDate: LocalDate?,
    val dueTime: LocalTime?,
    val isDone: Boolean = false
)

fun validateTask(uiState: TaskCreationUIState): Boolean {
    /*
       This function is responsible for checking if a Task is "Valid" and can be saved.
       // in service of the workshop
       todo on TaskCreationViewModel::saveTask() maybe have this function return a error message
         screen and use a switch on UIState to produce the right string
    */
    return uiState.spoons != 0 && uiState.title != ""
}

enum class Category {
    NONE,
    HYGIENE,
    WORK,
    SCHOOL
}

enum class Priority {

    low,
    medium,
    high,
    critical
}
