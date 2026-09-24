@file:Suppress("MagicNumber")

package dev.react2help.spooncheck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.dashboard_spoon_goal_bg

private val CardCornerRadius = 16.dp
private val CardHeight = 180.dp
private val ProgressIndicatorSize = 80.dp
private val ProgressStrokeWidth = 6.dp

/**
 * Full-width card showing the user's daily spoon goal with a teal background artwork,
 * a circular progress indicator, and a "View Tasks" shortcut button.
 *
 * @param progress 0.0–1.0 completion fraction (e.g. 0.8 = 80%).
 * @param completed number of spoons already consumed.
 * @param total total spoon budget for the day.
 * @param onViewTasks invoked when the "View Tasks" button is tapped.
 */
@Composable
fun DashboardSpoonGoalCard(
    progress: Float,
    completed: Int,
    total: Int,
    onViewTasks: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(CardHeight)
                .clip(RoundedCornerShape(CardCornerRadius)),
    ) {
        Image(
            painter = painterResource(Res.drawable.dashboard_spoon_goal_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Row(
            modifier =
                Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 36.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SpoonGoalTextColumn(
                completed = completed,
                total = total,
                onViewTasks = onViewTasks,
            )
            SpoonGoalProgress(progress = progress)
        }
    }
}

@Composable
private fun SpoonGoalTextColumn(
    completed: Int,
    total: Int,
    onViewTasks: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Daily Spoon Goal",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "$completed/$total spoons completed",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
        OutlinedButton(
            onClick = onViewTasks,
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
        ) {
            Text("View Tasks")
        }
    }
}

@Composable
private fun SpoonGoalProgress(progress: Float) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(ProgressIndicatorSize),
            color = Color.White,
            trackColor = Color.White.copy(alpha = 0.3f),
            strokeWidth = ProgressStrokeWidth,
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 220)
@Composable
private fun DashboardSpoonGoalCardPreview() {
    DashboardSpoonGoalCard(
        progress = 0.8f,
        completed = 8,
        total = 10,
        onViewTasks = {},
    )
}
