package dev.react2help.spooncheck.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.repositories.SampleTasks
import dev.react2help.spooncheck.theme.DashboardTextTeal
import dev.react2help.spooncheck.theme.DeepTeal
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
import spooncheck.shared.generated.resources.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24

private val CardCornerRadius = 12.dp
private val CardElevation = 4.dp
private val CardPadding = 16.dp
private val TitleBottomPadding = 12.dp
private val DividerVerticalPadding = 8.dp

private val PanelEndRadius = 12.dp
private val SwipeContentPadding = 16.dp
private val SwipeLabelSpacing = 8.dp

private val DescriptionSpacing = 4.dp
private val DescriptionEndPadding = 8.dp
private const val DescriptionMaxLines = 2

private val MetaIconSize = 14.dp
private val MetaPanelSpacing = 2.dp
private val MetaRowSpacing = 2.dp
private val MetaPanelHorizontalPadding = 8.dp
private val MetaPanelVerticalPadding = 6.dp
private val MinPanelWidth = 80.dp

private const val PreviewWidthDp = 400
private const val PreviewHeightDp = 280
private const val EmptyPreviewHeightDp = 160
private const val PreviewTaskCount = 2

/**
 * Card displaying a short list of high-priority tasks for today.
 *
 * Each task row shows the task title and description on the left and a priority-coloured metadata
 * panel on the right (spoon cost, priority label, optional time, and date). A row is completed by
 * swiping it to the right or by tapping its checkbox.
 *
 * @param tasks tasks to display (design shows top 2; no hard upper limit imposed here).
 * @param onTaskComplete called when the user swipes a task away or checks its checkbox.
 * @param onTaskClick called when the user taps a task row.
 */
@Composable
fun DashboardImportantTasksCard(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onTaskComplete: (Task) -> Unit = {},
    onTaskClick: (Task) -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
    ) {
        Column(modifier = Modifier.padding(CardPadding).animateContentSize()) {
            Text(
                text = "Important Tasks Today",
                style = MaterialTheme.typography.titleMedium,
                color = DashboardTextTeal,
                modifier = Modifier.padding(bottom = TitleBottomPadding),
            )
            if (tasks.isEmpty()) {
                Text(
                    text = "All important tasks are done. Nice work!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DashboardTextTeal,
                )
            }
            tasks.forEachIndexed { index, task ->
                // keyed so swipe state follows the task, not its position in the list
                key(task.id) {
                    SwipeToCompleteTaskRow(
                        task = task,
                        onComplete = { onTaskComplete(task) },
                        onClick = { onTaskClick(task) },
                    )
                }
                if (index < tasks.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = DividerVerticalPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun SwipeToCompleteTaskRow(
    task: Task,
    onComplete: () -> Unit,
    onClick: () -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = false,
        onDismiss = { onComplete() },
        backgroundContent = { CompleteSwipeBackground() },
    ) {
        DashboardTaskRow(
            task = task,
            onCompleteClick = onComplete,
            modifier = Modifier.background(Color.White).clickable(onClick = onClick),
        )
    }
}

@Composable
private fun CompleteSwipeBackground() {
    Row(
        modifier =
            Modifier.fillMaxSize()
                .clip(RoundedCornerShape(PanelEndRadius))
                .background(DeepTeal)
                .padding(horizontal = SwipeContentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SwipeLabelSpacing),
    ) {
        Icon(
            painter = painterResource(Res.drawable.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
            contentDescription = null,
            tint = Color.White,
        )
        Text(
            text = "Complete",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
        )
    }
}

@Composable
private fun DashboardTaskRow(
    task: Task,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = { onCompleteClick() },
            colors = CheckboxDefaults.colors(uncheckedColor = DashboardTextTeal),
            modifier = Modifier.semantics { contentDescription = "Mark ${task.title} complete" },
        )
        Column(
            modifier = Modifier.weight(1f).padding(end = DescriptionEndPadding),
            verticalArrangement = Arrangement.spacedBy(DescriptionSpacing),
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
                maxLines = DescriptionMaxLines,
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
                .padding(
                    horizontal = MetaPanelHorizontalPadding,
                    vertical = MetaPanelVerticalPadding,
                )
                .widthIn(min = MinPanelWidth),
        verticalArrangement = Arrangement.spacedBy(MetaPanelSpacing),
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
                iconRes = Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24,
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
        horizontalArrangement = Arrangement.spacedBy(MetaRowSpacing),
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

@Preview(showBackground = true, widthDp = PreviewWidthDp, heightDp = PreviewHeightDp)
@Composable
private fun DashboardImportantTasksCardPreview() {
    DashboardImportantTasksCard(tasks = SampleTasks.take(PreviewTaskCount))
}

@Preview(showBackground = true, widthDp = PreviewWidthDp, heightDp = EmptyPreviewHeightDp)
@Composable
private fun DashboardImportantTasksCardEmptyPreview() {
    DashboardImportantTasksCard(tasks = emptyList())
}
