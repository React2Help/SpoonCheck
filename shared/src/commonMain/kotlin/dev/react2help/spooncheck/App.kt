@file:Suppress(
    "LongMethod",
)

package dev.react2help.spooncheck

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository
import dev.react2help.spooncheck.repositories.SettingsThemeRepository
import dev.react2help.spooncheck.theme.SpoonCheckTheme
import dev.react2help.spooncheck.ui.DailyReflectionScreenGen
import dev.react2help.spooncheck.ui.DailyReflectionVariant
import dev.react2help.spooncheck.ui.SettingsScreenGen
import dev.react2help.spooncheck.ui.TaskCreationScreenGen
import dev.react2help.spooncheck.ui.TaskListScreen
import dev.react2help.spooncheck.viewmodels.DailyReflectionViewModel
import dev.react2help.spooncheck.viewmodels.SettingsViewModel
import dev.react2help.spooncheck.viewmodels.TaskCreationViewModel
import dev.react2help.spooncheck.viewmodels.TaskListViewModel

@Composable
@Preview
fun App() {
    // one shared theme repository for the whole app. It loads the saved theme from the device.
    val themeRepository = remember { SettingsThemeRepository() }
    val savedTheme by themeRepository.theme.collectAsStateWithLifecycle()

    // every screen inside the NavHost gets the saved theme's colors.
    SpoonCheckTheme(appTheme = savedTheme) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "settings") {
            composable("settings") {
                val viewModel = viewModel { SettingsViewModel(themeRepository) }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                // temp for demonstration. Eventually navigate from home page/setup day screen
                LaunchedEffect(state.wasCancelled) {
                    if (state.wasCancelled) navController.navigate("taskCreationDateDisabled")
                }

                LaunchedEffect(state.wasSaved) {
                    if (state.wasSaved) navController.navigate("roughDay")
                }

                SettingsScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state
                )
            }

            composable("taskCreationDateDisabled") {
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
                    isDateFieldEnabled = false
                )
            }

            composable("taskCreationDateEnabled") {
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
                    isDateFieldEnabled = true
                )
            }

            // daily Reflection variants
            composable("success") {
                val viewModel = viewModel { DailyReflectionViewModel() }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasSkipped) {
                    if (state.wasSkipped) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSubmitted) {
                    if (state.wasSubmitted) navController.navigate("taskList")
                }

                DailyReflectionScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state,
                    variant = DailyReflectionVariant.Success
                )
            }

            composable("successPartial") {
                val viewModel = viewModel { DailyReflectionViewModel() }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasSkipped) {
                    if (state.wasSkipped) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSubmitted) {
                    if (state.wasSubmitted) navController.navigate("taskList")
                }

                DailyReflectionScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state,
                    variant = DailyReflectionVariant.SuccessPartial
                )
            }

            composable("roughDay") {
                val viewModel = viewModel { DailyReflectionViewModel() }
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(state.wasSkipped) {
                    if (state.wasSkipped) navController.navigate("taskList")
                }

                LaunchedEffect(state.wasSubmitted) {
                    if (state.wasSubmitted) navController.navigate("taskList")
                }

                DailyReflectionScreenGen(
                    onAction = { action -> viewModel.onAction(action) },
                    state = state,
                    variant = DailyReflectionVariant.RoughDay
                )
            }

            composable("taskList") {
                val viewModel = viewModel { TaskListViewModel() }
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
