package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import dev.react2help.spooncheck.modelsandstate.DailyReflectionActions
import dev.react2help.spooncheck.modelsandstate.DailyReflectionUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DailyReflectionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DailyReflectionUIState())
    val uiState: StateFlow<DailyReflectionUIState> = _uiState.asStateFlow()

    fun onAction(action: DailyReflectionActions) {
        when (action) {
            is DailyReflectionActions.OnReflectionChanged ->
                _uiState.update { it.copy(reflectionText = action.text) }
            is DailyReflectionActions.OnSkip -> _uiState.update { it.copy(wasSkipped = true) }
            is DailyReflectionActions.OnSubmit ->
                // todo: save the reflection text somewhere (e.g. a repository) before leaving
                _uiState.update { it.copy(wasSubmitted = true) }
        }
    }
}
