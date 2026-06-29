package dev.react2help.spooncheck.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskCreationViewModel : ViewModel() {
    private val _formState = MutableStateFlow(TaskCreationUIState())
    val formState : StateFlow<TaskCreationUIState> = _formState.asStateFlow()

}