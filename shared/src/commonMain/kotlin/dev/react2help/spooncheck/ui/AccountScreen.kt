package dev.react2help.spooncheck.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("ModifierMissing")
@Composable
fun AccountScreen() {
    Scaffold { paddingValues ->
        Column(Modifier.padding(paddingValues).fillMaxWidth()) {
            Card(modifier = Modifier.fillMaxWidth()) { Text("Coming Soon!") }
        }
    }
}
