package dev.martinez.spooncheck.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.martinez.spooncheck.repositories.TaskRepository
import dev.martinez.spooncheck.repositories.ThemeRepository
import dev.martinez.spooncheck.ui.ViewTaskScreen
import dev.martinez.spooncheck.viewmodels.ViewTaskViewModel

@Suppress("ViewModelInjection")
@Composable
fun ViewTaskRoute(
    onNavigateBack: () -> Unit,
    taskId: Long,
    taskRepository: TaskRepository,
    themeRepository: ThemeRepository,
) {
    val viewmodel: ViewTaskViewModel =
        viewModel(
            factory =
                ViewTaskViewModel.provideFactory(
                    taskId,
                    taskRepository = taskRepository,
                    themeRepository,
                ),
        )
    val uiState = viewmodel.uiState.collectAsState()
    ViewTaskScreen({ onNavigateBack() }, uiState.value)
}
