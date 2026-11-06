package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import dev.react2help.spooncheck.modelsandstate.SettingsActions
import dev.react2help.spooncheck.modelsandstate.SettingsUIState
import dev.react2help.spooncheck.repositories.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(private val themeRepository: ThemeRepository) : ViewModel() {

    // start with saved theme selected
    private val _uiState =
        MutableStateFlow(SettingsUIState(selectedTheme = themeRepository.theme.value))
    val uiState: StateFlow<SettingsUIState> = _uiState.asStateFlow()

    fun onAction(action: SettingsActions) {
        when (action) {
            is SettingsActions.OnSave -> {
                // update theme for all screens to use
                themeRepository.saveTheme(_uiState.value.selectedTheme)
                _uiState.update { it.copy(wasSaved = true, hasUnsavedChanges = false) }
            }
            is SettingsActions.OnCancel ->
                // change not saved, do not update theme
                _uiState.update {
                    it.copy(
                        selectedTheme = themeRepository.theme.value,
                        wasCancelled = true,
                        hasUnsavedChanges = false
                    )
                }
            is SettingsActions.OnNotificationsChanged ->
                _uiState.update {
                    it.copy(notificationsEnabled = action.enabled, hasUnsavedChanges = true)
                }
            is SettingsActions.OnThemeChanged ->
                _uiState.update { it.copy(selectedTheme = action.theme, hasUnsavedChanges = true) }
            is SettingsActions.OnNameChanged ->
                _uiState.update { it.copy(userName = action.name, hasUnsavedChanges = true) }
            is SettingsActions.OnEmailChanged ->
                _uiState.update { it.copy(userEmail = action.email, hasUnsavedChanges = true) }
            is SettingsActions.OnPasswordChanged ->
                _uiState.update { it.copy(password = action.password, hasUnsavedChanges = true) }
            is SettingsActions.OnChangeName -> {
                /* opens editing — handled locally in UI */
            }
            is SettingsActions.OnChangeEmail -> {
                /* opens editing — handled locally in UI */
            }
            is SettingsActions.OnPasswordReset -> {
                /* todo: trigger password reset flow */
            }
            is SettingsActions.OnLogOut -> {
                /* todo: sign-out logic */
            }
            is SettingsActions.OnQrCodeScan -> {
                /* todo: launch QR scanner */
            }
        }
    }
}
