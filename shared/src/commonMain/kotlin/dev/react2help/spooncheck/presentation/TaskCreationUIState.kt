package dev.react2help.spooncheck.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock



sealed interface TaskCreationUIEvent { // what the user did: events that fire when the user does
    // something, like click a button or enter text in a field.
    data object BeginVoiceDetection : TaskCreationUIEvent
    data object SubmitTask : TaskCreationUIEvent
    data object ClearForm : TaskCreationUIEvent
    data object ExitForm : TaskCreationUIEvent

}
