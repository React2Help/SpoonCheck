package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import dev.react2help.spooncheck.modelsandstate.TaskListUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskListViewModel : ViewModel() { // todo
    private val _uiState = MutableStateFlow(TaskListUIState())
    val uiState: StateFlow<TaskListUIState> = _uiState.asStateFlow()
}
