package dev.react2help.spooncheck.routes

import androidx.compose.runtime.Composable
import dev.react2help.spooncheck.ui.PatternsScreen

@Composable
fun PatternsRoute(
    onNavigateToTaskCreationDateEnable: () -> Unit,
    onNavigateToPatterns: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToTaskList: () -> Unit
) {
    PatternsScreen(
        onNavigateToPatterns = { onNavigateToPatterns() },
        onNavigateToTaskCreationDateEnable = { onNavigateToTaskCreationDateEnable() },
        onNavigateToDashboard = { onNavigateToDashboard() },
        onNavigateToSettings = { onNavigateToSettings() },
        onNavigateToTaskList = { onNavigateToTaskList() }
    )
}
