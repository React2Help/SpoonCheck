package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.react2help.spooncheck.repositories.ThemeRepository
import dev.react2help.spooncheck.ui.SettingsScreenGen
import dev.react2help.spooncheck.viewmodels.SettingsViewModel

@Composable
fun SettingsRoute(
    themeRepository: ThemeRepository,
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToTaskCreationDateDisable: () -> Unit,
    viewModel: SettingsViewModel = viewModel { SettingsViewModel(themeRepository) }
) {
    val uiState = viewModel.uiState.collectAsState()
    SettingsScreenGen(
        onAction = { action -> viewModel.onAction(action) },
        state = uiState.value,
        { onNavigateToTaskCreationDateEnable },
        { onNavigateToTaskCreationDateDisable }
    )
}
