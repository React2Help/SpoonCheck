package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.TaskListActions
import dev.react2help.spooncheck.modelsandstate.TaskListUIState
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
What needs to be done:
1. define a callback function for UI to call which handleUIs  events

 */
class TaskListViewModel(
    private val taskRepository: TaskRepository = InMemoryTaskRepository() // Dependency Inversion
) : ViewModel() { // todo
    private val _uiState = MutableStateFlow(TaskListUIState())
    val uiState: StateFlow<TaskListUIState> = _uiState.asStateFlow()

    init {
        /*
        obtain the list of tasks from the repository when the viewmodel is created.
        happens immediately, before the UI is drawn, so the tasks are ready hopefully by the time
        the UI is cooked
        */
        viewModelScope.launch {
            taskRepository.tasks.collect { tasks ->
                _uiState.update { currentState -> currentState.copy(tasks = tasks) }
            }
        }
    }

    fun OnAction(action: TaskListActions) {
        when (action) {
            is TaskListActions.CompletionChanged -> TODO()
            is TaskListActions.onFilterOptionChange -> TODO()
            TaskListActions.NavigateToDashboard -> TODO()
            TaskListActions.NavigateToPatterns -> TODO()
            TaskListActions.NavigateToTaskCreation -> TODO()
            TaskListActions.NavigateToTaskList -> TODO()
            TaskListActions.NavigateToAccount -> TODO()
            is TaskListActions.onSpoonBudgetChange -> TODO()
            is TaskListActions.NavigateToTaskDetails -> TODO()
        }
    }
}
