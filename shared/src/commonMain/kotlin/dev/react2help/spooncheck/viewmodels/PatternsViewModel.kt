package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.DayRange
import dev.react2help.spooncheck.modelsandstate.PatternsScreenUIState
import dev.react2help.spooncheck.repositories.EventRepository
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PatternsViewModel(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    private val screenState = MutableStateFlow(PatternsScreenUIState())
    private val selectedCompletionChartRange = MutableStateFlow(DayRange.TWO_WEEKS)
    val uiState: StateFlow<PatternsScreenUIState> =
        combine(
                taskRepository.tasks,
                eventRepository.tasks,
                selectedCompletionChartRange,
                screenState
            ) { tasks, events, selectedCompletionChartRange, currentScreenState ->
                val completedEvents = events.count { event -> event.isDone }
                val completedTasks = tasks.count { task -> task.isDone }
                currentScreenState.copy(
                    completedEvents = completedEvents,
                    completedTasks = completedTasks,
                    completionsRange = selectedCompletionChartRange
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PatternsScreenUIState()
            )

    // TODO: Determine and implement relevant user actions
}
