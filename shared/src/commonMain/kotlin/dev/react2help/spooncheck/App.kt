package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

                // temp for demonstration. Eventually navigate from home page/setup day screen
                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskCreation?dateEnabled=false")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("taskCreation?dateEnabled=true")
                }

                SettingsScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state
                )
            }

            composable(
                route = "taskCreation?dateEnabled={dateEnabled}",
                arguments =
                    listOf(
                        navArgument("dateEnabled") {
                            type = NavType.BoolType
                            defaultValue = false
                        }
                    )
            ) { backStackEntry ->
                val dateEnabled = backStackEntry.arguments?.get("dateEnabled") as? Boolean ?: false
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
                    state = state,
                    isDateFieldEnabled = dateEnabled
                )
            }

            composable("taskList") {
                val viewModel = viewModel { TaskListViewModel() }
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
