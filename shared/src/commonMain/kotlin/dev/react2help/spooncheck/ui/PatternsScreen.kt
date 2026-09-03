@file:Suppress("LongMethod", "MaxLineLength", "PreviewPublic")

package dev.react2help.spooncheck.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.BaseAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.ShapeComponent
import dev.react2help.spooncheck.theme.PatternsTheme
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.logo
import spooncheck.shared.generated.resources.spoon

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun PatternsScreen() {

    PatternsTheme {
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Patterns") },
                    actions = {
                        IconButton(
                            onClick = { println() },
                            shape = RectangleShape,
                            content = {
                                Icon(
                                    painter = painterResource(Res.drawable.spoon),
                                    contentDescription = "Account Button"
                                )
                            }
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = false,
                        onClick = { println() },
                        icon = {},
                        label = { Text("Dashboard") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {},
                        label = { Text("Tasks") }
                    )
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {},
                        label = { Text("Patterns") }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape
                ) {
                    Icon( // todo figure out why the logo is not rendering
                        painterResource(Res.drawable.logo),
                        contentDescription = "FAB"
                    )
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                Card(modifier = Modifier.padding(20.dp)) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text("Completed in the Last 7 Days", fontWeight = FontWeight.Bold)
                    }
                }
                Card(modifier = Modifier.padding(20.dp)) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text("Baseline Spoon Economy", fontWeight = FontWeight.Bold)
                    }
                }
                Card(modifier = Modifier.padding(20.dp)) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text("Insights", fontWeight = FontWeight.Bold)
                        Text(
                            "Hey There!\n\nNice work this week. You exceeded your goal! However, " +
                                "i belive getting started earlier would lead to many improvements. " +
                                "Additionally, I've noticed you have been forgetting to take the " +
                                "trash out on Tuesdys, see if you can make that a priority this " +
                                "week. Keep at it!"
                        )
                    }
                }
            }
        }
    }
}
/*
@Composable
fun InsightsCard(content: String, modifier: Modifier = Modifier){
    Card(
        modifier
            .padding(20.dp)
    ){
        Column(
            modifier
                .padding(15.dp)
        ) {
            Text("Insights")
            Text(content)
        }
    }
}

 */
@Preview
@Composable
fun InsightsCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(20.dp)) {
        Column(modifier = Modifier.padding(15.dp)) {
            // use filler text, random numbers, etc
            Text("Insights")
            Text(
                "Hey There!\n\nNice work this week. You exceeded your goal! However, i believe " +
                    "getting started earlier would lead to many improvements. Additionally, I've " +
                    "noticed you have been forgetting to take the trash out on Tuesdys, see if you " +
                    "can make that a priority this week. Keep at it!"
            )
        }
    }
}

@Composable
fun InsightsCard(
    content: String,
    modifier: Modifier = Modifier
) { // copy and paste the code, and wire in the UI state
    Card(modifier = modifier.padding(20.dp)) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text("Insights")
            Text(content)
        }
    }
}

data class ChartPoint(
    val x: Double,
    val y: Double,
)

@Composable
fun SimpleLineChart(
    points: List<ChartPoint>,
    modifier: Modifier = Modifier,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(points) {
        modelProducer.runTransaction {
            lineSeries {
                series(
                    x = points.map { it.x },
                    y = points.map { it.y },
                )
            }
        }
    }

    val point =
        LineCartesianLayer.Point(
            component =
                ShapeComponent(
                    shape = CircleShape,
                )
        )
    val line =
        LineCartesianLayer.rememberLine(
            pointProvider =
                LineCartesianLayer.PointProvider.single(
                    point,
                ),
        )

    val lineLayer =
        rememberLineCartesianLayer(
            lineProvider =
                LineCartesianLayer.LineProvider.series(
                    line,
                ),
        )

    CartesianChartHost(
        modifier = modifier,
        modelProducer = modelProducer,
        chart =
            rememberCartesianChart(
                lineLayer,
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
    )
}

@Preview(
    showBackground = true,
    widthDp = 400,
    heightDp = 300,
)
@Composable
private fun SimpleLineChartPreview() {
    val fakePoints =
        listOf(
            ChartPoint(x = 0.0, y = 2.0),
            ChartPoint(x = 1.0, y = 5.0),
            ChartPoint(x = 2.0, y = 3.0),
            ChartPoint(x = 3.0, y = 8.0),
            ChartPoint(x = 4.0, y = 6.0),
        )

    SimpleLineChart(
        points = fakePoints,
        modifier = Modifier.fillMaxWidth().height(250.dp),
    )
}
