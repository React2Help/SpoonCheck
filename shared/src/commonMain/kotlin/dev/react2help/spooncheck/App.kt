package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.routes.SettingsRoute
import dev.react2help.spooncheck.routes.TaskCreationRoute
import dev.react2help.spooncheck.routes.TaskListRoute
import dev.react2help.spooncheck.viewmodels.TaskCreationViewModel

@Suppress("LongMethod")
@Composable
fun App(taskRepository: TaskRepository) {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "taskList"
        ) { // todo migrate to NavGraph (Navigation3)
            composable("settings") { // viewmodel and state are managed by settings route
                SettingsRoute(
                    onNavigateToTaskCreationDateEnable = {
                        navController.navigate("taskCreationDateEnabled")
                    },
                    onNavigateToTaskCreationDateDisable = {
                        navController.navigate("taskCreationDateDisabled")
                    }
                )
            }
            composable("taskCreationDateDisabled") {
                val viewModel = viewModel { TaskCreationViewModel(taskRepository) }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("taskList")
                }

                TaskCreationRoute(
                    taskRepository = taskRepository,
                    isDateFieldEnabled = false,
                    onNavigateToTaskList = { navController.navigate("taskList") }
                )
            }
            composable("taskCreationDateEnabled") {
                val viewModel = viewModel { TaskCreationViewModel(taskRepository) }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("taskList")
                }

                TaskCreationRoute(
                    taskRepository,
                    true,
                    onNavigateToTaskList = { navController.navigate("taskList") }
                )
            }

            composable("taskList") {
                TaskListRoute(
                    taskRepository,
                    { navController.navigate("taskCreationDateEnabled") },
                    onNavigateToPatterns = { TODO() },
                    onNavigateToDashboard = { TODO() },
                    onNavigateToAccount = { TODO() },
                    onNavigateToTaskDetails = { TODO() }
                )
            }
        }
    }
}
