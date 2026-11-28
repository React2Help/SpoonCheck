package dev.martinez.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.martinez.spooncheck.repositories.EventRepository
import dev.martinez.spooncheck.repositories.TaskRepository
import dev.martinez.spooncheck.ui.PatternsScreen
import dev.martinez.spooncheck.viewmodels.PatternsViewModel

@Suppress("ViewModelInjection")
@Composable
fun PatternsRoute(
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToPatterns: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToTaskList: () -> Unit,
    eventRepository: EventRepository,
    taskRepository: TaskRepository,
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
        uiState = uiState.value,
    )
}
