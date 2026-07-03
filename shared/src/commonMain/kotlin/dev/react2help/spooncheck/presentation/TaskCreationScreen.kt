package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
    fun TaskCreationScreen(
            ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Create Task", fontWeight = FontWeight.Bold) }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()

            ){
                TextField(
                    state = rememberTextFieldState(),
                    placeholder = {
                        Text("Title")
                    },
                    label = {
                        Text("Hello World!")
                    }
                )
                TextField(
                    state = rememberTextFieldState(),
                    placeholder = {
                        Text("Description")
                    }
                )
            }
        }
    }

@Composable
fun PrioritySelectButton(modifier: Modifier = Modifier){
    var selectedIndex by remember {mutableIntStateOf(0)}
    val options = listOf("low", "medium", "high", "critical")
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {selectedIndex = index},
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}