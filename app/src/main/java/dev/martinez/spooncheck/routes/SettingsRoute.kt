package dev.martinez.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.martinez.spooncheck.repositories.ThemeRepository
import dev.martinez.spooncheck.ui.SettingsScreenGen
import dev.martinez.spooncheck.viewmodels.SettingsViewModel

@Composable
fun SettingsRoute(
    themeRepository: ThemeRepository,
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToTaskCreationDateDisable: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel { SettingsViewModel(themeRepository) },
) {
    val uiState = viewModel.uiState.collectAsState()
    SettingsScreenGen(
        onAction = { action -> viewModel.onAction(action) },
        state = uiState.value,
        { onNavigateToTaskCreationDateEnable },
        { onNavigateToTaskCreationDateDisable },
        onNavigateBack = { onNavigateBack() },
    )
}
