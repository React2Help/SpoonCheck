package dev.react2help.spooncheck.modelsandstate

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.TimePickerState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

public const val MaxSpoons = 5

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
    val tasks: List<dev.react2help.spooncheck.database.Task> = emptyList(),
    val listFilterOption: TaskListFilterOptions = TaskListFilterOptions.Todo,
    val spoonBudget: Int = 1,
    val shouldNavigateToDashboard: Boolean = false,
    val shouldNavigateToPatterns: Boolean = false,
    val shouldNavigateToTaskCreation: Boolean = false,
    val shouldNavigateToAccount: Boolean = false,

    // todo add a field for the Icon of the Account button. Pending learning how to do this.
    val errorMessage: String? = null
)

sealed interface TaskListActions {
    /*
    TaskList ViewModel is not responsible for handling the following events:
    - expanding / collapsing a section within the task list. This is handled by the component logic.
     */
    data class onFilterOptionChange(val filterOption: TaskListFilterOptions) : TaskListActions

    data object NavigateToAccount : TaskListActions

    data object NavigateToDashboard : TaskListActions

    data object NavigateToTaskList : TaskListActions // this should be a NO-OP

    data object NavigateToPatterns : TaskListActions

    data object NavigateToTaskCreation : TaskListActions

    data class NavigateToTaskDetails(val taskId: Long) : TaskListActions

    data class onSpoonBudgetChange(val newSpoonBudget: Int) : TaskListActions

    data class CompletionChanged(
        val taskId: Long,
        val isDone: Boolean,
    ) : TaskListActions
    // don't think I need to hoist the state for the list.
    // Until we implement more complex functionality.
    data class OnDelete(val taskId: Long) : TaskListActions
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

    val titleFieldState: TextFieldState,
    val descriptionFieldState: TextFieldState,
    val dateFieldState: TextFieldState,
    val timePickerState: TimePickerState,
    val titleError: Boolean,
    val dateError: Boolean,
    val priority: Priority = Priority.medium,
    val spoons: Int = 0,
    val notificationsOn: Boolean = false,
    val isRecurring: Boolean = false,
    val category: Category,
    // should startdate be nullable?
    val errorMessage: String = "",
    val wasSaved: Boolean = false,
    val wasCancelled: Boolean = false,

    // fields to support components in the form
    val selectedCategory: Int = Category.HYGIENE.ordinal, // supports the CategoryAndPriority card
    val selectedSpoons: Int = 1
)

sealed interface TaskCreationActions { // defining types for our actions, so the callback functions
    // must satisfy this contract
    data object Cancel : TaskCreationActions // since these actions don't need arguments, they are
    // specified as objects
    data class Save(val task: dev.react2help.spooncheck.database.Task) : TaskCreationActions

    data object ClearDateError : TaskCreationActions

    data class SetDateError(val value: Boolean) : TaskCreationActions

    data class SetTitleError(val value: Boolean) : TaskCreationActions

    data class OnNotificationsChanged(val shouldNotify: Boolean) : TaskCreationActions

    data class OnSpoonSelectedChanged(val spoons: Int) : TaskCreationActions

    data class OnCategoryChanged(val category: Category) : TaskCreationActions // category of a task

    data class OnPriorityChanged(val priority: Priority) : TaskCreationActions

    data class OnRecursChanged(val recurs: Boolean) : TaskCreationActions

    /*
    supports CategoryAndPriority card. This callback should not be confused with OnCategoryChanged.
    this callback is responsible for managing the selected option in the Category Dropdown menu.
    */
    data class OnSelectedCategoryChange(val selectedCategory: Int) : TaskCreationActions

    data class OnSelectedSpoonsChanged(val newSpoonCount: Int) : TaskCreationActions

    data class OnTimeChange(val newTime: TimePickerState) : TaskCreationActions
}

data class Task( // todo add other fields
    /*
       date and time are nullable because I believe the user should be able to create tasks with no
       due date or time.

       spoons default value is zero because I believe we should always encourage the user to think
       about how much effort a task consumes.


    */
    var id: Long,
    val title: String,
    val description: String,
    val spoons: Int = 0,
    val priority: Priority,
    val category: Category,
    val dueDate: LocalDate?,
    val dueTime: LocalTime?,
    val isDone: Boolean = false
)

enum class Category {
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

enum class AppTheme {
    FOREST,
    BEACH,
    DARK
}

data class SettingsUIState(
    val notificationsEnabled: Boolean = true,
    val selectedTheme: AppTheme = AppTheme.FOREST,
    val userName: String = "",
    val userEmail: String = "",
    val password: String = "",
    val hasUnsavedChanges: Boolean = false,
    val wasSaved: Boolean = false,
    val wasCancelled: Boolean = false
)

sealed interface SettingsActions {
    data class OnNotificationsChanged(val enabled: Boolean) : SettingsActions

    data class OnThemeChanged(val theme: AppTheme) : SettingsActions

    data object OnChangeName : SettingsActions

    data object OnChangeEmail : SettingsActions

    data object OnPasswordReset : SettingsActions

    data object OnLogOut : SettingsActions

    data object OnQrCodeScan : SettingsActions

    data object OnSave : SettingsActions

    data object OnCancel : SettingsActions

    data class OnNameChanged(val name: String) : SettingsActions

    data class OnEmailChanged(val email: String) : SettingsActions

    data class OnPasswordChanged(val password: String) : SettingsActions
}

// The three variants of the Daily Reflection screen, each with its own heading message.
enum class DailyReflectionVariant(val message: String) {
    Success("Congratulations!\nYou Completed All Your Tasks!"),
    SuccessPartial("Congratulations!\nYou Completed Most Of Your Tasks!"),
    RoughDay("Today might’ve been a hard day.\nThat’s Ok, take some time to reflect.")
}

data class DailyReflectionUIState(
    val reflectionText: String = "",
    val wasSkipped: Boolean = false,
    val wasSubmitted: Boolean = false
)

sealed interface DailyReflectionActions {
    data class OnReflectionChanged(val text: String) : DailyReflectionActions

    data object OnSkip : DailyReflectionActions

    data object OnSubmit : DailyReflectionActions
}
