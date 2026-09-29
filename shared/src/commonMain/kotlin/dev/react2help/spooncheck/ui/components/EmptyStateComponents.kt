package dev.react2help.spooncheck.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyTaskListComponent(padding: PaddingValues, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(padding),
        colors =
            CardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceDim,
                disabledContentColor = MaterialTheme.colorScheme.onSurface
            )
    ) {
        Text("You have no tasks.")
    }
}

@Preview
@Composable
private fun EmptyTaskListComponentPreview() {
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Preview") }) }) { paddingValues ->
        EmptyTaskListComponent(paddingValues)
    }
}
