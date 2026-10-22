package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.TaskListActions
import dev.react2help.spooncheck.modelsandstate.TaskListFilterOptions
import dev.react2help.spooncheck.modelsandstate.TaskListUIState
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
What needs to be done:
1. define a callback function for UI to call which handleUIs  events

 */
class TaskListViewModel(
    private val taskRepository: TaskRepository = InMemoryTaskRepository() // Dependency Inversion
) : ViewModel() {
    private val selectedFilter = MutableStateFlow(TaskListFilterOptions.Todo)
    private val screenState = MutableStateFlow(TaskListUIState())
    val uiState: StateFlow<TaskListUIState> =
        combine(
            taskRepository.tasks,
            selectedFilter,
            screenState
        ){ tasks, filter, currentScreenState ->
            val filteredTasks = tasks.filter { task ->
                when(filter) {
                    TaskListFilterOptions.ALL_TASKS -> true
                    TaskListFilterOptions.Todo -> !task.isDone
                    TaskListFilterOptions.Done -> task.isDone
                }
            }
            currentScreenState.copy(
                tasks = filteredTasks,
                listFilterOption = filter
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TaskListUIState()
        )



    fun OnAction(action: TaskListActions) {
        when (action) {
            is TaskListActions.CompletionChanged ->
                viewModelScope.launch { taskRepository.setCompleted(action.taskId, action.isDone) }
            is TaskListActions.onFilterOptionChange ->
                selectedFilter.value = action.filterOption
            TaskListActions.NavigateToDashboard ->
                screenState.update { currentState ->
                    currentState.copy(shouldNavigateToDashboard = true)
                }
            TaskListActions.NavigateToPatterns ->
                screenState.update { currentState ->
                    currentState.copy(shouldNavigateToPatterns = true)
                }
            TaskListActions.NavigateToTaskCreation ->
                screenState.update { currentState ->
                    currentState.copy(shouldNavigateToTaskCreation = true)
                }
            TaskListActions.NavigateToTaskList -> Unit
            is TaskListActions.OnDelete ->
                viewModelScope.launch { taskRepository.deleteOne(action.taskId) }
            TaskListActions.NavigateToAccount ->
                screenState.update { currentState ->
                    currentState.copy(shouldNavigateToAccount = true)
                }
            is TaskListActions.onSpoonBudgetChange ->
                screenState.update { currentState ->
                    currentState.copy(spoonBudget = action.newSpoonBudget)
                }
            is TaskListActions.NavigateToTaskDetails -> TODO()
        }
    }
}
