@file:Suppress(
    "LongMethod",
    "MagicNumber",
    "ModifierMissing",
    "ModifierNotUsedAtRoot",
    "ModifierReused",
    "PreviewPublic",
)

package dev.react2help.spooncheck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.pine_tree_background

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DailyReflectionScreen(variant: DailyReflectionVariant) {
    MaterialTheme {
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
                                    contentColor = Color(0xFF254A50),
                                    containerColor = Color(0xFFD4E2E3)
                                ),
                            shape = RoundedCornerShape(8.dp),
                        )
                        Button(
                            onClick = { /* Handle click */},
                            content = { Text("Submit") },
                            colors =
                                ButtonDefaults.buttonColors(
                                    contentColor = Color(0xFF254A50),
                                    containerColor = Color(0xFFD4E2E3)
                                ),
                            shape = RoundedCornerShape(8.dp),
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box( // use a box so the fields are stacked on top of the image
            ) {
                Image(
                    painter = painterResource(Res.drawable.pine_tree_background),
                    contentDescription = "Background Image of a grove of pine trees.",
                    contentScale = ContentScale.Crop, // scale the image so it fills the screen and
                    // the parts that overflow off the screen are clipped
                    modifier = Modifier.fillMaxHeight()
                )
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
}

@Preview
@Composable
fun DailyReflectionScreen() {
    DailyReflectionScreen(variant = DailyReflectionVariant.Success)
}

@Preview
@Composable
fun DailyReflectionScreenSuccessPartialPreview() {
    DailyReflectionScreen(variant = DailyReflectionVariant.SuccessPartial)
}

@Preview
@Composable
fun DailyReflectionScreenRoughDayPreview() {
    DailyReflectionScreen(variant = DailyReflectionVariant.RoughDay)
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
                color = Color(0xFF254A50),
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
                        focusedIndicatorColor = Color(0xFF2E4F57),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Black
                    ),
            )
        }
    }
}
