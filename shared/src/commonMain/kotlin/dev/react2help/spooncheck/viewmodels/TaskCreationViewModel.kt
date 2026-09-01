package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import dev.react2help.spooncheck.modelsandstate.TaskCreationActions
import dev.react2help.spooncheck.modelsandstate.TaskCreationUIState
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlin.time.Clock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TaskCreationViewModel(
    @Suppress("UnusedPrivateProperty") private val taskRepository: TaskRepository
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
            is TaskCreationActions.OnSave -> {
                /*
                   1. Validate
                   2. persist // todo build out data layer
                   3. report success or failure // todo
                */
                if (_uiState.value.title == "") {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = "Title Cannot Be Blank!")
                    }
                    return
                } else if (
                    _uiState.value.startDate <
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
                ) {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = "Start Date Cannot Be Before Today!")
                    }
                }
                // todo
                return
            }
            is TaskCreationActions.OnDelete -> { // todo
            }
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
            is TaskCreationActions.OnRecurrsChanged -> {
                _uiState.update { currentState -> currentState.copy(isRecurring = action.recurs) }
            }
        }
    }
}
