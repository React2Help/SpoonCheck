package dev.react2help.spooncheck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Suppress("MagicNumber")
@Composable
fun SwipeableTruncatedTaskCard(
    task: Task,
    swipeRightAllowed: Boolean,
    swipeLeftAllowedd: Boolean,
    onTransfer: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    /*
       This card can be swiped to add / remove it from a list
       A task card with only a limited amount of information showing. Meant to be used in low-available
       space layouts.
       - priority, denoted by background color
       - title
       - description
       - spoons

    */

    val color: Color =
        when (task.priority) { // todo define these colors
            Priority.low -> Color.Gray
            Priority.medium -> Color.White
            Priority.high -> Color.Cyan
            Priority.critical -> Color.Red
        }
    var offsetX by remember { mutableFloatStateOf(0f) }
    val dragThreshold = 120f
    Card(
        modifier =
            modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta -> offsetX += delta },
                    onDragStopped = {
                        val shouldTransfer =
                            when {
                                swipeRightAllowed && offsetX > dragThreshold -> true
                                swipeLeftAllowedd && offsetX < -dragThreshold -> true
                                else -> false
                            }

                        if (shouldTransfer) {
                            onTransfer(task.id)
                        }

                        offsetX = 0f
                    }
                )
    ) {
        Row( // sides go next to each other
            modifier =
                Modifier.background(
                        if (offsetX != 0f) Color.LightGray.copy(alpha = 0.3f) else color
                    )
                    .padding(10.dp)
        ) {
            Column( // LHS
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium
                ) // todo apply styling?
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Column( // RHS
                modifier = Modifier.weight(1f)
            ) {
                Text("${task.spoons} Spoons", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun TruncatedTaskCard(task: Task, modifier: Modifier = Modifier) {
    /*
    A task card with only a limited amount of information showing. Meant to be used in low-available
    space layouts.
    - priority, denoted by background color
    - title
    - description
    - spoons
     */
    val color: Color =
        when (task.priority) { // todo define these colors
            Priority.low -> Color.Gray
            Priority.medium -> Color.White
            Priority.high -> Color.Cyan
            Priority.critical -> Color.Red
        }

    Card(modifier = modifier) {
        Row( // sides go next to each other
            modifier = Modifier.background(color = color).padding(10.dp)
        ) {
            Column( // LHS
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium
                ) // todo apply styling?
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Column( // RHS
                modifier = Modifier.weight(1f)
            ) {
                Text("${task.spoons} Spoons", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Suppress("LongMethod", "MagicNumber")
@Preview
@Composable
private fun PreviewAll() {

    val dueDate: LocalDate =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val dueTime: LocalTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    Task(
        id = 1L,
        title = "Task Title",
        description = "A Description with enough words to be a description. Wow",
        spoons = 2,
        priority = Priority.high,
        category = Category.HYGIENE,
        dueDate = dueDate,
        dueTime = dueTime,
        isDone = false
    )
    var leftList: List<Task> =
        List(10) { i ->
            Task(
                id = i.toLong(),
                title = "Left Task #${i}",
                description = "Description for task #${i}",
                spoons = Random.nextInt(1, 10),
                priority = Priority.entries[i % Priority.entries.size],
                category = Category.entries[i % Category.entries.size],
                dueDate = dueDate,
                dueTime = dueTime,
                isDone = i % 2 == 0
            )
        }
    val rightList =
        List(10) { i ->
            Task(
                id = (i + 10).toLong(),
                title = "Right Task #${i}",
                description = "Description for task #${i}",
                spoons = Random.nextInt(1, 10),
                priority = Priority.entries[i % Priority.entries.size],
                category = Category.entries[i % Category.entries.size],
                dueDate = dueDate,
                dueTime = dueTime,
                isDone = i % 2 == 0
            )
        }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Preview of All Task Card Components") }) }
    ) { paddingValues ->
        Row {
            LazyColumn(modifier = Modifier.weight(1f).padding(paddingValues)) {
                items(leftList) {
                    SwipeableTruncatedTaskCard(
                        it,
                        true,
                        false,
                        { id -> leftList = leftList.filter { task -> task.id != id } }
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn(modifier = Modifier.weight(1f).padding(paddingValues)) {
                items(rightList) { SwipeableTruncatedTaskCard(it, false, true, { id -> }) }
            }
        }
    }
}
