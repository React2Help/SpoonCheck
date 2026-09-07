@file:Suppress("LongMethod", "MaxLineLength", "PreviewPublic", "MagicNumber")

package dev.react2help.spooncheck.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.columnModel
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.ShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent

private val ChartBlue = Color(0xFF6F9BAA)
private val ChartDarkBlue = Color(0xFF315E69)

data class DailyCompletion(
    val day: String,
    val completedTasks: Int,
    val completedEvents: Int,
)

data class SpoonEconomyPoint(
    val day: String,
    val spoons: Double,
)

@Composable
fun CompletedLastSevenDaysChart(
    data: List<DailyCompletion>,
    modifier: Modifier = Modifier,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(data) {
        modelProducer.runTransaction {
            columnModel {
                series(y = data.map { it.completedTasks })
                series(y = data.map { it.completedEvents })
            }
        }
    }

    val dayFormatter =
        remember(data) {
            CartesianValueFormatter { _, value, _ -> data.getOrNull(value.toInt())?.day.orEmpty() }
        }
    val taskColumn = rememberLineComponent(Fill(ChartBlue), thickness = 5.dp, shape = CircleShape)
    val eventColumn =
        rememberLineComponent(Fill(ChartDarkBlue), thickness = 5.dp, shape = CircleShape)
    val totalTasks = data.sumOf { it.completedTasks }
    val totalEvents = data.sumOf { it.completedEvents }

    ChartCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = "Completed in the Last 7 Days",
                style = MaterialTheme.typography.labelMedium,
                color = ChartDarkBlue,
            )
            Column {
                Text("$totalTasks Task\nSpoons", color = ChartBlue, fontSize = 10.sp)
                Text(
                    text = "$totalEvents Event\nSpoons",
                    modifier = Modifier.padding(top = 8.dp),
                    color = ChartDarkBlue,
                    fontSize = 10.sp,
                )
            }
        }

        CartesianChartHost(
            modifier = Modifier.fillMaxWidth().height(90.dp),
            modelProducer = modelProducer,
            chart =
                rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        columnProvider =
                            ColumnCartesianLayer.ColumnProvider.series(taskColumn, eventColumn),
                        mergeMode = { ColumnCartesianLayer.MergeMode.Grouped(2.dp) },
                    ),
                    startAxis = VerticalAxis.rememberStart(),
                    bottomAxis =
                        HorizontalAxis.rememberBottom(
                            valueFormatter = dayFormatter,
                            itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() },
                            guideline = null
                        ),
                ),
            scrollState = rememberVicoScrollState(scrollEnabled = true)
        )
    }
}

@Composable
fun BaselineSpoonEconomyChart(
    points: List<SpoonEconomyPoint>,
    modifier: Modifier = Modifier,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(points) {
        modelProducer.runTransaction {
            lineModel {
                series(
                    x = points.indices.map { it.toDouble() },
                    y = points.map { it.spoons },
                )
            }
        }
    }

    val dayFormatter =
        remember(points) {
            CartesianValueFormatter { _, value, _ ->
                points.getOrNull(value.toInt())?.day.orEmpty()
            }
        }
    val point = remember {
        LineCartesianLayer.Point(component = ShapeComponent(shape = CircleShape), size = 4.dp)
    }
    val line =
        LineCartesianLayer.rememberLine(
            fill = LineCartesianLayer.LineFill.single(Fill(Color(0xAA6F9BAA))),
            pointProvider = LineCartesianLayer.PointProvider.single(point),
        )

    ChartCard(modifier = modifier) {
        Text(
            text = "Baseline Spoon Economy",
            style = MaterialTheme.typography.labelMedium,
            color = ChartDarkBlue,
        )
        CartesianChartHost(
            modifier = Modifier.fillMaxWidth().height(130.dp),
            modelProducer = modelProducer,
            chart =
                rememberCartesianChart(
                    rememberLineCartesianLayer(
                        lineProvider = LineCartesianLayer.LineProvider.series(line),
                    ),
                    startAxis = VerticalAxis.rememberStart(),
                    bottomAxis =
                        HorizontalAxis.rememberBottom(
                            valueFormatter = dayFormatter,
                            itemPlacer = remember { HorizontalAxis.ItemPlacer.aligned() },
                            guideline = null
                        ),
                ),
            scrollState = rememberVicoScrollState(scrollEnabled = false)
        )
    }
}

@Composable
private fun ChartCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            content = content,
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 500)
@Composable
private fun PatternChartsPreview() {
    val completions =
        listOf(
            DailyCompletion("M", 12, 8),
            DailyCompletion("Tu", 14, 10),
            DailyCompletion("We", 10, 7),
            DailyCompletion("Th", 13, 9),
            DailyCompletion("F", 11, 7),
            DailyCompletion("S", 7, 4),
            DailyCompletion("Su", 6, 3),
        )
    val economy =
        listOf(10, 14, 19, 16, 22, 25, 17, 15, 13, 11, 20, 28, 21, 16).mapIndexed { index, value ->
            SpoonEconomyPoint(
                listOf("M", "Tu", "We", "Th", "F", "S", "Su")[index % 7],
                value.toDouble()
            )
        }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CompletedLastSevenDaysChart(completions, Modifier.fillMaxWidth().height(150.dp))
        BaselineSpoonEconomyChart(economy, Modifier.fillMaxWidth().height(190.dp))
    }
}
