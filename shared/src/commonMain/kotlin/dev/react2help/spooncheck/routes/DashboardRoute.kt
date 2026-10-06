package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.ui.DashboardScreen
import dev.react2help.spooncheck.viewmodels.DashboardViewModel

@Composable
fun DashboardRoute(taskRepository: TaskRepository) {
    val viewModel: DashboardViewModel = viewModel {
        DashboardViewModel(taskRepository = taskRepository)
    }
    val uiState = viewModel.uiState.collectAsState()
    DashboardScreen(state = uiState.value, onAction = {})
}
