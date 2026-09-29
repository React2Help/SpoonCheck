package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.TaskListActions
import dev.react2help.spooncheck.modelsandstate.TaskListFilterOptions
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
                var filteredTasks =
                    tasks.filter { task ->
                        when (_uiState.value.listFilterOption) {
                            TaskListFilterOptions.ALL_TASKS -> true
                            TaskListFilterOptions.Todo -> !task.isDone
                            TaskListFilterOptions.Done -> task.isDone
                        }
                    }
                _uiState.update { currentState -> currentState.copy(tasks = filteredTasks) }
            }
        }
    }

    fun OnAction(action: TaskListActions) {
        when (action) {
            is TaskListActions.CompletionChanged ->
                viewModelScope.launch { taskRepository.setCompleted(action.taskId, action.isDone) }
            is TaskListActions.onFilterOptionChange ->
                _uiState.update { currentState ->
                    viewModelScope.launch { // recalculate what tasks to show with the new filiter
                        taskRepository.tasks.collect { tasks ->
                            var filteredTasks =
                                tasks.filter { task ->
                                    when (action.filterOption) {
                                        TaskListFilterOptions.ALL_TASKS -> true
                                        TaskListFilterOptions.Todo -> !task.isDone
                                        TaskListFilterOptions.Done -> task.isDone
                                    }
                                }
                            _uiState.update { currentState ->
                                currentState.copy(tasks = filteredTasks)
                            }
                        }
                    }
                    currentState.copy(listFilterOption = action.filterOption)
                }
            TaskListActions.NavigateToDashboard ->
                _uiState.update { currentState ->
                    currentState.copy(shouldNavigateToDashboard = true)
                }
            TaskListActions.NavigateToPatterns ->
                _uiState.update { currentState ->
                    currentState.copy(shouldNavigateToPatterns = true)
                }
            TaskListActions.NavigateToTaskCreation ->
                _uiState.update { currentState ->
                    currentState.copy(shouldNavigateToTaskCreation = true)
                }
            TaskListActions.NavigateToTaskList -> {
                /* purposely a noop. We are already on the task list screen.*/
            }
            is TaskListActions.OnDelete ->
                viewModelScope.launch { taskRepository.deleteOne(action.taskId) }
            TaskListActions.NavigateToAccount ->
                _uiState.update { currentState ->
                    currentState.copy(shouldNavigateToAccount = true)
                }
            is TaskListActions.onSpoonBudgetChange ->
                _uiState.update { currentState ->
                    currentState.copy(spoonBudget = action.newSpoonBudget)
                }
            is TaskListActions.NavigateToTaskDetails -> TODO()
        }
    }
}
