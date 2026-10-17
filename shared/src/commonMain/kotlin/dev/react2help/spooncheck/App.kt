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
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.ui.SettingsScreenGen
import dev.react2help.spooncheck.ui.TaskCreationScreenGen
import dev.react2help.spooncheck.ui.TaskListScreen
import dev.react2help.spooncheck.viewmodels.SettingsViewModel
import dev.react2help.spooncheck.viewmodels.TaskCreationViewModel
import dev.react2help.spooncheck.viewmodels.TaskListViewModel

@Composable
fun App(taskRepository: TaskRepository) {
    MaterialTheme {
        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = "taskList") {
            composable("settings") {
                val viewModel = viewModel { SettingsViewModel() }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                // temp for demonstration. Eventually navigate from home page/setup day screen
                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskCreationDateDisabled")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("taskCreationDateEnabled")
                }

                SettingsScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state
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

                TaskCreationScreenGen(viewModel = viewModel, isDateFieldEnabled = false)
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

                TaskCreationScreenGen(viewModel = viewModel, isDateFieldEnabled = true)
            }

            composable("taskList") {
                val viewModel = viewModel { TaskListViewModel(taskRepository) }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.shouldNavigateToTaskCreation) {
                    if (state.shouldNavigateToTaskCreation)
                        navController.navigate("taskCreationDateEnabled")
                }
                /*
                TODO: implement navigation to other screens
                 */
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
