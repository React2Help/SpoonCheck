package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import spooncheck.shared.generated.resources.Res

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
    fun TaskCreationScreen(
            ) {
    val datePickerState =
        rememberDatePickerState(Clock.System.now().toEpochMilliseconds(), initialDisplayMode = DisplayMode.Input)

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
                    }
                )
                TextField(
                    state = rememberTextFieldState(),
                    placeholder = {
                        Text("Description")
                    }
                )

                DatePicker(state = datePickerState)
                TextField(
                    state = rememberTextFieldState(),
                    placeholder = {Text("1 Spoon")}
                )

            }
        }
    }


