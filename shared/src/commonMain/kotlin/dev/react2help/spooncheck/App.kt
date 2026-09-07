package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository
import dev.react2help.spooncheck.ui.SettingsScreenGen
import dev.react2help.spooncheck.ui.TaskCreationScreenGen
import dev.react2help.spooncheck.ui.TaskListScreen
import dev.react2help.spooncheck.viewmodels.SettingsViewModel
import dev.react2help.spooncheck.viewmodels.TaskCreationViewModel
import dev.react2help.spooncheck.viewmodels.TaskListViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "settings") {
            composable("settings") {
                val viewModel = viewModel { SettingsViewModel() }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("taskList")
                }

                SettingsScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state
                )
            }

            composable("taskCreation") {
                val viewModel = viewModel { TaskCreationViewModel(InMemoryTaskRepository()) }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("taskList")
                }

                TaskCreationScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state
                )
            }

            composable("taskList") {
                val viewModel = viewModel { TaskListViewModel() }
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
