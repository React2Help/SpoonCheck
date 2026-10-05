@file:Suppress(
    "LongMethod",
    "MagicNumber",
    "ModifierMissing",
    "ModifierNotUsedAtRoot",
    "ModifierReused",
    "PreviewPublic",
)

package dev.react2help.spooncheck.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.react2help.spooncheck.modelsandstate.AppTheme
import dev.react2help.spooncheck.modelsandstate.DailyReflectionActions
import dev.react2help.spooncheck.modelsandstate.DailyReflectionUIState
import dev.react2help.spooncheck.theme.SpoonCheckTheme
import dev.react2help.spooncheck.theme.ThemedBackground
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DailyReflectionScreen(variant: DailyReflectionVariant) {
    Scaffold(
        topBar = { // define the Header
            TopAppBar(
                title = { Text("Evening Check-in", fontWeight = FontWeight.Bold) },
                subtitle = { Text("") },
                titleHorizontalAlignment = Alignment.CenterHorizontally
            )
        },
        bottomBar = { // define the two buttons at the bottom of the screen
            BottomAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { /* Handle click */},
                        content = { Text("Skip") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                        shape = RoundedCornerShape(8.dp),
                    )
                    Button(
                        onClick = { /* Handle click */},
                        content = { Text("Submit") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                        shape = RoundedCornerShape(8.dp),
                    )
                }
            }
        }
    ) { paddingValues ->
        Box( // use a box so the fields are stacked on top of the image
        ) {
            ThemedBackground() // pine trees / ocean / solid dark, depending on the theme
            Row(
                modifier =
                    Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckInCard(variant = variant)
            }
        }
    }
}

@Preview
@Composable
fun DailyReflectionScreen() {
    SpoonCheckTheme(appTheme = AppTheme.FOREST) {
        DailyReflectionScreen(variant = DailyReflectionVariant.Success)
    }
}

@Preview
@Composable
fun DailyReflectionScreenSuccessPartialPreview() {
    SpoonCheckTheme(appTheme = AppTheme.FOREST) {
        DailyReflectionScreen(variant = DailyReflectionVariant.SuccessPartial)
    }
}

@Preview
@Composable
fun DailyReflectionScreenRoughDayPreview() {
    SpoonCheckTheme(appTheme = AppTheme.FOREST) {
        DailyReflectionScreen(variant = DailyReflectionVariant.RoughDay)
    }
}

@Preview
@Composable
private fun DailyReflectionScreenBeachPreview() {
    SpoonCheckTheme(appTheme = AppTheme.BEACH) {
        DailyReflectionScreen(variant = DailyReflectionVariant.Success)
    }
}

@Preview
@Composable
private fun DailyReflectionScreenDarkPreview() {
    SpoonCheckTheme(appTheme = AppTheme.DARK) {
        DailyReflectionScreen(variant = DailyReflectionVariant.Success)
    }
}

@Composable
fun CheckInCard(
    modifier: Modifier = Modifier,
    variant: DailyReflectionVariant = DailyReflectionVariant.Success
) {

    val inquiryState = rememberTextFieldState()
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = modifier.padding(12.dp).fillMaxWidth()) {
            Text(
                variant.message,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 23.sp,
                textAlign = TextAlign.Center
            )
            TextField(
                state = inquiryState,
                placeholder = { Text("How Did You Feel About Today?") },
                trailingIcon = {
                    Icon(
                        painter =
                            painterResource(
                                Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                            ),
                        contentDescription = "Clear description",
                        modifier = Modifier.size(14.dp).clickable { inquiryState.clearText() }
                    )
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
            )
        }
    }
}

// production version
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DailyReflectionScreenGen(
    onAction: (DailyReflectionActions) -> Unit,
    state: DailyReflectionUIState,
    variant: DailyReflectionVariant
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evening Check-in", fontWeight = FontWeight.Bold) },
                subtitle = { Text("") },
                titleHorizontalAlignment = Alignment.CenterHorizontally
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onAction(DailyReflectionActions.OnSkip) },
                        content = { Text("Skip") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                        shape = RoundedCornerShape(8.dp),
                    )
                    Button(
                        onClick = { onAction(DailyReflectionActions.OnSubmit) },
                        content = { Text("Submit") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                        shape = RoundedCornerShape(8.dp),
                    )
                }
            }
        }
    ) { paddingValues ->
        Box {
            ThemedBackground()
            Row(
                modifier =
                    Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckInCard(
                    reflectionText = state.reflectionText,
                    variant = variant,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun CheckInCard(
    reflectionText: String,
    variant: DailyReflectionVariant,
    onAction: (DailyReflectionActions) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
            Text(
                variant.message,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 23.sp,
                textAlign = TextAlign.Center
            )
            // The text lives in the view model: every keystroke is sent up as an action, and the
            // field shows whatever state.reflectionText currently is.
            TextField(
                value = reflectionText,
                onValueChange = { onAction(DailyReflectionActions.OnReflectionChanged(it)) },
                placeholder = { Text("How Did You Feel About Today?") },
                trailingIcon = {
                    Icon(
                        painter =
                            painterResource(
                                Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                            ),
                        contentDescription = "Clear description",
                        modifier =
                            Modifier.size(14.dp).clickable {
                                onAction(DailyReflectionActions.OnReflectionChanged(""))
                            }
                    )
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
            )
        }
    }
}

@Preview
@Composable
private fun DailyReflectionScreenGenRoughDayPreview() {
    SpoonCheckTheme(appTheme = AppTheme.FOREST) {
        DailyReflectionScreenGen(
            onAction = {},
            state = DailyReflectionUIState(),
            variant = DailyReflectionVariant.RoughDay
        )
    }
}
