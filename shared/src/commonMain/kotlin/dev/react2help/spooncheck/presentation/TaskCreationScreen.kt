package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
                Text("Hello World!")
            }
        }
    }