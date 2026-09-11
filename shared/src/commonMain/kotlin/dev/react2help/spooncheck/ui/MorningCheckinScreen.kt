package dev.react2help.spooncheck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.logo

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MorningCheckinScreen(
    userFeelings: TextFieldState,
    numHoursSlept: TextFieldState,
    modifier: Modifier = Modifier,
    saveAndExit: () -> Unit = {},
    navigateToManageTasks: () -> Unit = {},
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = saveAndExit, shape = RoundedCornerShape(8.dp)) {
                        Text("Save and Exit Check-in")
                    }
                    Button(onClick = navigateToManageTasks, shape = RoundedCornerShape(8.dp)) {
                        Text("Manage Today's Tasks")
                    }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Background Image",
                alpha = 0.5f,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RectangleShape)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp).fillMaxSize()
            ) {
                Text("How Much Sleep Did You Get?")
                TextField(numHoursSlept)
                Text("How Do You Feel Right Now?")
                TextField(userFeelings)
            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    MorningCheckinScreen(rememberTextFieldState(), rememberTextFieldState())
}
