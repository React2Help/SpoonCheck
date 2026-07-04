package dev.react2help.spooncheck.presentation


sealed interface TaskListUIEvent { // what the user did
    data object LoadTasks : TaskListUIEvent
    data class ToggleTask(val id: String) : TaskListUIEvent
    data object ClearError : TaskListUIEvent
}
