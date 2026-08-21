package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import dev.react2help.spooncheck.modelsandstate.TaskCreationActions
import dev.react2help.spooncheck.modelsandstate.TaskCreationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class TaskCreationViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(TaskCreationUIState())
    val uiState: StateFlow<TaskCreationUIState> = _uiState.asStateFlow()
    // again, all these functions should perform validation as needed but right now we just assume the new value is "valid" and slap it in

    fun OnAction(Action: TaskCreationActions){ //
        /*
        defining these callback functions with types means we can rest assured whatever
        parameters a particular Action requires will be there without any "does this parameter -
        exist?" The magic of compilers and compile time!
        It all looks quite boilerplate, and it is, but that's what you get! At some point you
        just accept the boilerplate.
        */
        when(Action){
            is TaskCreationActions.OnSave -> {
                /*
                    1. Validate
                    2. persist // todo build out data layer
                    3. report success or failure // todo
                 */
                if (_uiState.value.Title == ""){
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessage = "Title Cannot Be Blank!"
                        )
                    }
                    return
                }else if(_uiState.value.StartDate < Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time){
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessage = "Start Date Cannot Be Before Today!"
                        )
                    }
                }
                // todo
                return
            }
            is TaskCreationActions.OnDelete -> { // todo

            }
            is TaskCreationActions.OnTitleChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        Title = Action.Title
                    )
                }
            }
            is TaskCreationActions.OnDueDateChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        DueDate = Action.DueDate
                    )
                }
            }
            is TaskCreationActions.OnCategoryChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        Category = Action.Category
                    )
                }
            }
            is TaskCreationActions.OnDescriptionChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        Description = Action.Description
                    )
                }
            }
            is TaskCreationActions.OnNotificationsChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        NotificationsOn = Action.ShouldNotify
                    )
                }
            }
            is TaskCreationActions.OnPriorityChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        priority = Action.Priority
                    )
                }
            }
            is TaskCreationActions.OnSpoonSelectedChanged -> { // todo
                _uiState.update { currentState ->
                    currentState.copy(
                        Spoons = Action.Spoons
                    )
                }
            }
            is TaskCreationActions.OnDueTimeChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        StartDate = Action.DueTime
                    )
                }
            }
            is TaskCreationActions.OnRecurrsChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        IsRecurring = Action.Recurs
                    )

                }
            }
        }
    }
}