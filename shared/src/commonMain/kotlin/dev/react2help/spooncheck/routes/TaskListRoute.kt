package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.ui.TaskListScreen
import dev.react2help.spooncheck.viewmodels.TaskListViewModel

@Composable
@Suppress("ViewModelInjection")
fun TaskListRoute(
    taskRepository: TaskRepository,
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToPatterns: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToTaskDetails: () -> Unit
) {
    val viewModel: TaskListViewModel = viewModel { TaskListViewModel(taskRepository) }
    TaskListScreen(
        viewModel = viewModel,
        onNavigateToTaskCreationDateEnable = onNavigateToTaskCreationDateEnable,
        onNavigateToPatterns = onNavigateToPatterns,
        onNavigateToDashboard = onNavigateToDashboard,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToTaskDetails = onNavigateToTaskDetails
    )
}
