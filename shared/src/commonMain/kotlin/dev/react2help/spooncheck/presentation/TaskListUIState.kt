package dev.react2help.spooncheck.presentation

data class TaskListUIState( // what the screen displays
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null
)
sealed interface TaskListUIEvent { // what the user did
    data object LoadTasks : TaskListUIEvent
    data class ToggleTask(val id: String) : TaskListUIEvent
    data object ClearError : TaskListUIEvent
}
