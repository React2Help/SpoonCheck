package dev.react2help.spooncheck.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class TaskCreationViewModel : ViewModel() {
    private val _formState = MutableStateFlow(TaskCreationUIState())
    val formState : StateFlow<TaskCreationUIState> = _formState.asStateFlow()
    // again, all these functions should perform validation as needed but right now we just assume the new value is "valid" and slap it in
    fun onTitleChange(newValue: String) {
        _formState.update {
            it.copy(
                Title = newValue
            )
        }
    }
    fun onDescriptionChange(newValue: String){
        _formState.update {
            it.copy(
                Description = newValue
            )
        }
    }
    fun onPriorityChange(newValue: Priority){
        _formState.update {
            it.copy(
                priority = newValue
            )
        }
    }
    fun onSpoonsChange(newValue: Int){
        _formState.update {
            it.copy(
                Spoons = newValue
            )
        }
    }
    fun onDueDateChange(newValue: LocalDate){
        _formState.update {
            it.copy(
                DueDate = newValue
            )
        }
    }
    fun submitTask(){
        val shouldError: Boolean = true
        // insert logic for if a task is a valid task. Show an error if it is not. probs end up
        // being title, due date, spoons at a minimum
        if(shouldError){
            _formState.update {
                it.copy(
                    errorMessage = "Invalid Task. Please create a valid task"
                )
            }
        }
    }

}