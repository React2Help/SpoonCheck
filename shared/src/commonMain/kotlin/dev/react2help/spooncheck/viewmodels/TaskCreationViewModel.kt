package dev.react2help.spooncheck.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.modelsandstate.TaskCreationActions
import dev.react2help.spooncheck.modelsandstate.TaskCreationUIState
import dev.react2help.spooncheck.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskCreationViewModel(
    @Suppress("UnusedPrivateProperty") private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskCreationUIState(
        category = Category.HYGIENE,
        titleFieldState = TextFieldState(),
        descriptionFieldState = TextFieldState(),
        timeFieldState = TextFieldState(),
        dateFieldState = TextFieldState(),
        titleError = false,
        timeError = false,
        dateError = false
    ))
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
            is TaskCreationActions.Save -> saveTask(task = action.task)
            is TaskCreationActions.Cancel -> cancelTask()

            is TaskCreationActions.OnCategoryChanged -> {
                _uiState.update { currentState -> currentState.copy(category = action.category) }
            }

            is TaskCreationActions.OnNotificationsChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(notificationsOn = action.shouldNotify)
                }
            }
            is TaskCreationActions.OnPriorityChanged -> {
                _uiState.update { currentState -> currentState.copy(priority = action.priority) }
            }
            is TaskCreationActions.OnSpoonSelectedChanged -> {
                _uiState.update { currentState -> currentState.copy(spoons = action.spoons) }
            }
            is TaskCreationActions.OnRecursChanged -> {
                _uiState.update { currentState -> currentState.copy(isRecurring = action.recurs) }
            }

            TaskCreationActions.ClearDateError -> _uiState.update { currentState->currentState.copy(dateError = false) }
            TaskCreationActions.ClearTimeError -> _uiState.update { currentState->currentState.copy(timeError = false) }
            is TaskCreationActions.SetDateError -> _uiState.update { currentState -> currentState.copy(dateError = action.value) }
            is TaskCreationActions.SetTimeError -> _uiState.update { currentState->currentState.copy(timeError = action.value) }
            is TaskCreationActions.SetTitleError -> _uiState.update { currentState-> currentState.copy(titleError = action.value) }
        }
    }

    private fun cancelTask() { // todo
        /*
        What needs to be done:
        1. figure out which what pressing the cancel button should do.
            a. does it clear the form?
            b. navigate the user away from the task creation screen?
                i. this function currently triggers navigating to the Task List screen.
                wasCancelled is listened on by a coroutine defined in App.kt,
                wasCancelled == true -> navigate(tasklistscreen)
            c. should there be a third button responsible for merely clearing the form?
         */
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

    private fun saveTask(task: Task) { // todo
        /*
        What is already completed:
        1. The function triggers navigation back to the task list screen.
            wasSaved is listened on by a coroutine defined in App.kt
            wasSaved == true -> navigate(taskListScreen)
        What needs to be done:
        1. This function needs to trigger persisting the task by the data layer.
            // DONE
        2. This function needs to relay success and error messages from the data layer
            // TODO: error messages need to be relayed
        3. This function needs to accept a task as a parameter and return success or failure to UI
            // DONE
         */
        // validation is handled in the UI before this is called.  Then save and navigate


        viewModelScope.launch {
            val saveStatus = repository.save(task)

            if (saveStatus.isSuccess){
                updateState { copy(wasSaved = true) }
            }else{
                // additionally copy saveStatus' error message to UI state.
                updateState { copy(wasSaved = false) }
            }

        }

    }
}
