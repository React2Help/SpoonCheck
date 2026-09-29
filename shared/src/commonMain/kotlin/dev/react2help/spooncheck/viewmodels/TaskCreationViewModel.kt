package dev.react2help.spooncheck.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
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
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class TaskCreationViewModel(
    @Suppress("UnusedPrivateProperty") private val repository: TaskRepository
) : ViewModel() {
    /*
    KNOWN BUGS
    - selecting spoons has no effect. Created tasks do not properly have their spoons displayed.
    Always states zero spoons.
    - Task list doesn't respect the category of a task. always puts it in Hygiene
     */
    @OptIn(ExperimentalMaterial3Api::class)
    private val _uiState =
        MutableStateFlow(
            TaskCreationUIState(
                category = Category.HYGIENE,
                titleFieldState = TextFieldState(),
                descriptionFieldState = TextFieldState(),
                dateFieldState = TextFieldState(),
                titleError = false,
                dateError = false,
                timePickerState = TimePickerState(
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour,
                    initialMinute = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).minute,
                    is24Hour = false
                )
            )
        )
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
            TaskCreationActions.ClearDateError ->
                _uiState.update { currentState -> currentState.copy(dateError = false) }
            is TaskCreationActions.SetDateError ->
                _uiState.update { currentState -> currentState.copy(dateError = action.value) }
            is TaskCreationActions.SetTitleError ->
                _uiState.update { currentState -> currentState.copy(titleError = action.value) }
            is TaskCreationActions.OnSelectedCategoryChange ->
                _uiState.update { currentState ->
                    currentState.copy(selectedCategory = action.selectedCategory)
                }
            is TaskCreationActions.OnSelectedSpoonsChanged ->
                _uiState.update { currentState ->
                    currentState.copy(selectedSpoons = action.newSpoonCount)
                }

            is TaskCreationActions.OnTimeChange -> _uiState.update { currentState -> currentState.copy(timePickerState = action.newTime) }
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

            if (saveStatus.isSuccess) {
                updateState { copy(wasSaved = true) }
            } else {
                // additionally copy saveStatus' error message to UI state.
                updateState { copy(wasSaved = false) }
            }
        }
    }
}
