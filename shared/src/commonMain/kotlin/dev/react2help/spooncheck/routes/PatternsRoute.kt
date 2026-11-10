package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.react2help.spooncheck.repositories.EventRepository
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.ui.PatternsScreen
import dev.react2help.spooncheck.viewmodels.PatternsViewModel

@Suppress("ViewModelInjection")
@Composable
fun PatternsRoute(
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToPatterns: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToTaskList: () -> Unit,
    eventRepository: EventRepository,
    taskRepository: TaskRepository
) {
    val viewModel: PatternsViewModel = viewModel {
        PatternsViewModel(taskRepository, eventRepository)
    }
    val uiState = viewModel.uiState.collectAsState()
    PatternsScreen(
        onNavigateToPatterns = { onNavigateToPatterns() },
        onNavigateToTaskCreationDateEnable = { onNavigateToTaskCreationDateEnable() },
        onNavigateToDashboard = { onNavigateToDashboard() },
        onNavigateToSettings = { onNavigateToSettings() },
        onNavigateToTaskList = { onNavigateToTaskList() },
        uiState = uiState.value
    )
}
