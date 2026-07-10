package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialShapes.Companion.Oval
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.WavyProgressIndicatorDefaults.trackColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.spoon

@Preview
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
){


}

@Preview
@Composable
fun SpoonBudgetCard(modifier: Modifier = Modifier){
    // passing in a modifier as the last optional argument is apparently standard practice
    Card(
        modifier = modifier
            .sizeIn(
                maxWidth = 359.dp,
                maxHeight = 122.dp
            )
            .dropShadow(shape = RectangleShape, Shadow(radius = 4.dp, offset = DpOffset(0.dp, 4.dp))),
        colors = CardDefaults.cardColors(
            containerColor = Color(0XFFF1FAFA)
        )
    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ){
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()

            ){
                Text(
                    "Today's Spoon Budget",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier
                    .size(10.dp)
                )
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF6B4BB3)
                    ),
                    modifier = Modifier
                        .size(89.dp, 32.dp)
                        .dropShadow(
                            shape = CircleShape,
                            Shadow(3.dp)
                        )
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(Res.drawable.spoon),
                            contentDescription = "An icon of a spoon",
                            modifier = Modifier
                                .size(16.dp)
                        )
                        Text(
                            "5/10",
                            fontWeight = FontWeight.Bold
                        )

                    }

                }
            }
            Text(
                "You have a moderate energy day",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF5F6B7A)
            )
            Spacer(
                modifier = Modifier.size(33.dp)
            )
            SpoonsUsedIndicator()
        }

        }
}
@Preview
@Composable
fun SpoonsUsedIndicator(modifier: Modifier = Modifier){
    Column{
        LinearDeterminateIndicator()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                "5 left",
             color = Color(0xFF5F6B7A)
            )
            Text(
                "5 used",
                color = Color(0xFF5F6B7A)
            )
        }
    }
}
@Preview
@Composable
fun LinearDeterminateIndicator() {
    var currentProgress by remember { mutableFloatStateOf(0.5f) }
    // TODO add function for computing or retrieving spoons used / spoons allotted
    var loading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(
            progress = { currentProgress },
            modifier = Modifier.fillMaxWidth().height(7.dp),
            trackColor = Color(0xFF167C84),
            color = Color(0xFFBDB4E8),
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
        )
    }
}

