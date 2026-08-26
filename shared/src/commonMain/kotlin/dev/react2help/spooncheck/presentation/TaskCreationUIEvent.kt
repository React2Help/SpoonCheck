package dev.react2help.spooncheck.presentation

sealed interface TaskCreationUIEvent { // what the user did: events that fire when the user does
    // something, like click a button or enter text in a field.
    data object BeginVoiceDetection : TaskCreationUIEvent

    data object SubmitTask : TaskCreationUIEvent

    data object ClearForm : TaskCreationUIEvent

    data object ExitForm : TaskCreationUIEvent
}
