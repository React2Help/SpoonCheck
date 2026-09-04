package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import dev.react2help.spooncheck.ui.TaskListScreen

@Composable
@Preview
fun App() {
    MaterialTheme { TaskListScreen() }
}
