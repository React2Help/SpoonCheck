package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.react2help.spooncheck.ui.SettingsScreenGen
import dev.react2help.spooncheck.viewmodels.SettingsViewModel

@Composable
fun SettingsRoute(
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToTaskCreationDateDisable: () -> Unit,
    viewModel: SettingsViewModel = viewModel { SettingsViewModel() }
) { // pass in a repository if it becomes necessary in the future
    val uiState = viewModel.uiState.collectAsState()
    SettingsScreenGen(
        onAction = { action -> viewModel.onAction(action) },
        state = uiState.value,
        { onNavigateToTaskCreationDateEnable },
        { onNavigateToTaskCreationDateDisable }
    )
}
