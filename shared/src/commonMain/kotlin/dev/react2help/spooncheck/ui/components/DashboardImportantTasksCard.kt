@file:Suppress("MagicNumber")

package dev.react2help.spooncheck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.theme.DashboardTextTeal
import dev.react2help.spooncheck.theme.PriorityHighBg
import dev.react2help.spooncheck.theme.PriorityVeryHighBg
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24

private val CardCornerRadius = 12.dp
private val PanelEndRadius = 12.dp
private val MetaIconSize = 14.dp
private val MinPanelWidth = 80.dp

/**
 * Card displaying a short list of high-priority tasks for today.
 *
 * Each task row shows the task title and description on the left and a
 * priority-coloured metadata panel on the right (spoon cost, priority label,
 * optional time, and date).
 *
 * @param tasks tasks to display (design shows top 2; no hard upper limit imposed here).
 */
@Composable
fun DashboardImportantTasksCard(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Important Tasks Today",
                style = MaterialTheme.typography.titleMedium,
                color = DashboardTextTeal,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            tasks.forEachIndexed { index, task ->
                DashboardTaskRow(task = task)
                if (index < tasks.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun DashboardTaskRow(task: Task, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                color = DashboardTextTeal,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = DashboardTextTeal,
                maxLines = 2,
            )
        }
        DashboardTaskMetaPanel(task = task)
    }
}

private fun priorityPanelColor(priority: Priority) =
    when (priority) {
        Priority.critical -> PriorityVeryHighBg
        else -> PriorityHighBg
    }

private fun priorityLabel(priority: Priority) =
    when (priority) {
        Priority.critical -> "Very High"
        Priority.high -> "High"
        Priority.medium -> "Medium"
        Priority.low -> "Low"
    }

private fun formatTime(time: LocalTime): String =
    time.format(
        LocalTime.Format {
            amPmHour(padding = Padding.ZERO)
            char(':')
            minute()
            char(' ')
            amPmMarker("AM", "PM")
        }
    )

private fun formatDate(date: LocalDate): String =
    date.format(
        LocalDate.Format {
            monthNumber()
            char('/')
            day()
        }
    )

@Composable
private fun DashboardTaskMetaPanel(task: Task) {
    Column(
        modifier =
            Modifier.clip(
                RoundedCornerShape(
                    topEnd = PanelEndRadius,
                    bottomEnd = PanelEndRadius,
                )
            )
                .background(priorityPanelColor(task.priority))
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .widthIn(min = MinPanelWidth),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        TaskMetaRow(
            iconRes = Res.drawable.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24,
            iconDescription = "Priority",
            label = priorityLabel(task.priority),
        )
        TaskMetaRow(
            iconRes = Res.drawable.spoon,
            iconDescription = "Spoon cost",
            label = "${task.spoons}",
        )
        task.dueTime?.let { time ->
            TaskMetaRow(
                iconRes = Res.drawable.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24,
                iconDescription = "Time",
                label = formatTime(time),
            )
        }
        task.dueDate?.let { date ->
            TaskMetaRow(
                iconRes =
                    Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24,
                iconDescription = "Date",
                label = formatDate(date),
            )
        }
    }
}

@Composable
private fun TaskMetaRow(
    iconRes: DrawableResource,
    iconDescription: String,
    label: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = iconDescription,
            modifier = Modifier.size(MetaIconSize),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 280)
@Composable
private fun DashboardImportantTasksCardPreview() {
    val tasks =
        listOf(
            Task(
                id = 1L,
                title = "Pay Rent",
                description = "This is super important, do not forget!",
                spoons = 3,
                priority = Priority.critical,
                category = Category.NONE,
                dueDate = LocalDate(2025, 9, 22),
                dueTime = LocalTime(10, 22, 0),
            ),
            Task(
                id = 2L,
                title = "Clean The Dishes",
                description = "Wash everything on the left side of the sink",
                spoons = 2,
                priority = Priority.high,
                category = Category.NONE,
                dueDate = LocalDate(2025, 9, 22),
                dueTime = null,
            ),
        )
    DashboardImportantTasksCard(tasks = tasks)
}
