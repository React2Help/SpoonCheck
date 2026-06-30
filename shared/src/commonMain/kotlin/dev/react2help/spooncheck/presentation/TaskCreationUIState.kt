package dev.react2help.spooncheck.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

data class TaskCreationUIState(
    val isLoading: Boolean = false, // tracks if this UI is loading or not
    val isListening: Boolean = false, // boolean for tracking UI state for if the microphone is
    // listening for Voice input. Pressing the microphone button fires off a "listening for audio"
    // event which then mutates this variable to true. Other UI elements change their behavior based
    // on this variable to signal the microphone is listening.
    // ---


    // fields for the data in the form
    val Title: String = "",
    val Description: String = "",
    val priority: Priority = Priority.low,
    val DueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val Spoons: Int = 0,
    val errorMessage: String = ""
)

sealed interface TaskCreationUIEvent { // what the user did: events that fire when the user does
    // something, like click a button or enter text in a field.
    data object BeginVoiceDetection : TaskCreationUIEvent
    data object SubmitTask : TaskCreationUIEvent
    data object ClearForm : TaskCreationUIEvent
    data object ExitForm : TaskCreationUIEvent

}
