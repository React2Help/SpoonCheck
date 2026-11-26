package dev.martinez.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.martinez.spooncheck.repositories.TaskRepository
import dev.martinez.spooncheck.ui.TaskListScreen
import dev.martinez.spooncheck.viewmodels.TaskListViewModel

@Composable
@Suppress("ViewModelInjection")
fun TaskListRoute(
    taskRepository: TaskRepository,
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToPatterns: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToTaskDetails: (Long) -> Unit
) {
    val viewModel: TaskListViewModel = viewModel { TaskListViewModel(taskRepository) }
    TaskListScreen(
        viewModel = viewModel,
        onNavigateToTaskCreationDateEnable = onNavigateToTaskCreationDateEnable,
        onNavigateToPatterns = onNavigateToPatterns,
        onNavigateToDashboard = onNavigateToDashboard,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToTaskDetails = onNavigateToTaskDetails
    )
}
