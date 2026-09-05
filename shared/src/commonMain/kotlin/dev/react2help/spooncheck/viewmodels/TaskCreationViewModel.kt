package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.modelsandstate.TaskCreationActions
import dev.react2help.spooncheck.modelsandstate.TaskCreationUIState
import dev.react2help.spooncheck.modelsandstate.validateTask
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskCreationViewModel(
    @Suppress("UnusedPrivateProperty") private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskCreationUIState())
    val uiState: StateFlow<TaskCreationUIState> = _uiState.asStateFlow()
    // again, all these functions should perform validation as needed but right now we just assume
    // the new value is "valid" and slap it in

    fun onAction(action: TaskCreationActions) { //
        /*
        defining these callback functions with types means we can rest assured whatever
        parameters a particular Action requires will be there without any "does this parameter -
        exist?" The magic of compilers and compile time!
        It all looks quite boilerplate, and it is, but that's what you get! At some point you
        just accept the boilerplate.
        */
        when (action) {
            is TaskCreationActions.Save -> saveTask()
            is TaskCreationActions.Cancel -> cancelTask()
            is TaskCreationActions.OnTitleChanged -> {
                _uiState.update { currentState -> currentState.copy(title = action.title) }
            }
            is TaskCreationActions.OnDueDateChanged -> {
                _uiState.update { currentState -> currentState.copy(dueDate = action.dueDate) }
            }
            is TaskCreationActions.OnCategoryChanged -> {
                _uiState.update { currentState -> currentState.copy(category = action.category) }
            }
            is TaskCreationActions.OnDescriptionChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(description = action.description)
                }
            }
            is TaskCreationActions.OnNotificationsChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(notificationsOn = action.shouldNotify)
                }
            }
            is TaskCreationActions.OnPriorityChanged -> {
                _uiState.update { currentState -> currentState.copy(priority = action.priority) }
            }
            is TaskCreationActions.OnSpoonSelectedChanged -> { // todo
                _uiState.update { currentState -> currentState.copy(spoons = action.spoons) }
            }
            is TaskCreationActions.OnDueTimeChanged -> {
                _uiState.update { currentState -> currentState.copy(startDate = action.dueTime) }
            }
            is TaskCreationActions.OnRecursChanged -> {
                _uiState.update { currentState -> currentState.copy(isRecurring = action.recurs) }
            }
        }
    }

    private fun cancelTask() {
        updateState { copy(wasCancelled = true) }
    }

    private fun updateState(transform: TaskCreationUIState.() -> TaskCreationUIState) {
        _uiState.update(transform)
    }

    /*
    private fun resetForm() {
        _uiState.value = TaskCreationUIState()
    }
    */
    private fun nextTaskId(): Long = repository.tasks.value.maxOfOrNull(Task::id)?.plus(1) ?: 1L

    private fun saveTask() {
        val current = _uiState.value
        // validate
        // attempt save
        // report errors else report success
        if (!validateTask(current)) { // TODO: workshop error message. Make it field aware.
            updateState { copy(errorMessage = "Please input a valid task.") }
            return
        }
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = "") }

            try {
                repository.save(
                    Task(
                        id = nextTaskId(),
                        title = current.title,
                        description = current.description,
                        spoons = current.spoons,
                        priority = current.priority,
                        category = current.category,
                        dueDate = current.dueDate,
                        dueTime = current.dueTime,
                        isDone = false
                    )
                )
                updateState { copy(isLoading = false, wasSaved = true) }
            } catch (_: Exception) {
                updateState { copy(isLoading = false) }
            }
        }
    }
}
