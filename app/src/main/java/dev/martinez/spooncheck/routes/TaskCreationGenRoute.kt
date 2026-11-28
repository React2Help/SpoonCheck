package dev.martinez.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.martinez.spooncheck.repositories.TaskRepository
import dev.martinez.spooncheck.ui.TaskCreationScreenGen
import dev.martinez.spooncheck.viewmodels.TaskCreationViewModel

@Composable
@Suppress("ViewModelInjection")
fun TaskCreationRoute(
    taskRepository: TaskRepository,
    isDateFieldEnabled: Boolean,
    onNavigateToTaskList: () -> Unit,
) {
    val viewModel: TaskCreationViewModel = viewModel { TaskCreationViewModel(taskRepository) }
    val uiState by viewModel.uiState.collectAsState()
    TaskCreationScreenGen(
        uiState = uiState,
        onAction = { action -> viewModel.onAction(action) },
        isDateFieldEnabled = isDateFieldEnabled,
        onNavigateToTaskList = { onNavigateToTaskList() },
    )
}
