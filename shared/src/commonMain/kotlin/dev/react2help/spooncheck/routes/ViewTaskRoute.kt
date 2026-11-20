package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.repositories.ThemeRepository
import dev.react2help.spooncheck.ui.ViewTaskScreen
import dev.react2help.spooncheck.viewmodels.ViewTaskViewModel

@Suppress("ViewModelInjection")
@Composable
fun ViewTaskRoute(
    onNavigateBack: () -> Unit,
    taskId: Long,
    taskRepository: TaskRepository,
    themeRepository: ThemeRepository
) {
    val viewmodel: ViewTaskViewModel =
        viewModel(
            factory =
                ViewTaskViewModel.provideFactory(
                    taskId,
                    taskRepository = taskRepository,
                    themeRepository
                )
        )
    val uiState = viewmodel.uiState.collectAsState()
    ViewTaskScreen({ onNavigateBack() }, uiState.value)
}
