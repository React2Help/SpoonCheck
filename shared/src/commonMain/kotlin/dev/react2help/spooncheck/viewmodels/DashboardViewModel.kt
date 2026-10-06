package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.DashboardActions
import dev.react2help.spooncheck.modelsandstate.DashboardUIState
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val MaxImportantTasks = 2
private const val StopTimeoutMillis = 5_000L

// todo replace with real values once morning check-ins and the user profile exist
private const val PlaceholderUserName = "Benjamin"
private const val PlaceholderCheckins = 4
private const val PlaceholderRestDays = 1

class DashboardViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val uiState: StateFlow<DashboardUIState> =
        taskRepository.tasks
            .map(::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(StopTimeoutMillis),
                initialValue = toUiState(taskRepository.tasks.value),
            )

    // Navigation actions are handled by the caller (see App.kt); only data actions land here.
    fun onAction(action: DashboardActions) {
        when (action) {
            is DashboardActions.OnTaskCompletionChanged ->
                viewModelScope.launch { taskRepository.setCompleted(action.taskId, action.isDone) }
            is DashboardActions.OnTaskClick,
            DashboardActions.OnViewTasks,
            DashboardActions.OnFabClick,
            is DashboardActions.OnDestinationSelect -> Unit
        }
    }

    private fun toUiState(tasks: List<Task>) =
        DashboardUIState(
            userName = PlaceholderUserName,
            consumedSpoons = tasks.filter { it.isDone }.sumOf { it.spoons },
            importantTasks = importantTasks(tasks),
            numCheckins = PlaceholderCheckins,
            numRestDays = PlaceholderRestDays,
        )

    private fun importantTasks(tasks: List<Task>) =
        tasks
            .filter { !it.isDone && it.priority >= Priority.high }
            .sortedWith(
                compareByDescending<Task> { it.priority }
                    .thenBy(nullsLast()) { it.dueDate }
                    .thenBy(nullsLast()) { it.dueTime }
            )
            .take(MaxImportantTasks)
}
