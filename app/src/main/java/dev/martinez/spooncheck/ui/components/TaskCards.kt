@file:Suppress("MagicNumber", "LongMethod", "UnusedPrivateMember", "TooGenericExceptionCaught", "SwallowedException")

package dev.martinez.spooncheck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.martinez.spooncheck.R
import dev.martinez.spooncheck.database.Task
import dev.martinez.spooncheck.modelsandstate.AppTheme
import dev.martinez.spooncheck.modelsandstate.Category
import dev.martinez.spooncheck.modelsandstate.Priority
import dev.martinez.spooncheck.theme.LocalAppTheme
import dev.martinez.spooncheck.theme.fourth
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Clock
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

const val THRESHOLD = 0.25f

@Composable
private fun priorityColor(priority: Priority): Color {
    val isDark = LocalAppTheme.current == AppTheme.DARK
    return when (priority) {
        Priority.Critical -> if (isDark) Color(0xFF2F4A50) else Color(0xFFD4E2E3)
        Priority.High -> if (isDark) Color(0xFF5A4632) else Color(0xFFEAD3B6)
        Priority.Medium,
        Priority.Low -> if (isDark) Color(0xFF4A4232) else Color(0xFFFEF2DC)
    }
}

@Composable
fun SwipeToDismissCard(
    onClick: (Long) -> Unit,
    onSwipeLeft: (Long) -> Unit,
    onSwipeRight: (Long) -> Unit,
    task: Task,
    modifier: Modifier = Modifier,
) {

    val state =
        rememberSwipeToDismissBoxState(
            positionalThreshold = { totalDistance -> totalDistance * THRESHOLD },
        )
    val scope = rememberCoroutineScope()
    var isWorking by remember { mutableStateOf(false) }
    SwipeToDismissBox(
        modifier = modifier,
        state = state,
        backgroundContent = {
            val color =
                when (state.dismissDirection) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        MaterialTheme.colorScheme.secondary
                    }
                    SwipeToDismissBoxValue.EndToStart -> {
                        MaterialTheme.colorScheme.error
                    }
                    SwipeToDismissBoxValue.Settled -> {
                        Color.Transparent
                    }
                }
            Box(
                modifier = Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                contentAlignment =
                    if (state.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
                        Alignment.CenterStart
                    else Alignment.CenterEnd,
            ) {
                if (isWorking) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(24.dp),
                    )
                } else {
                    if (state.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                        Icon(
                            painter = painterResource(R.drawable.done_outline_24dp),
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.size(24.dp),
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.delete_24dp),
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }
        },
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        gesturesEnabled = state.settledValue == SwipeToDismissBoxValue.Settled && !isWorking,
        onDismiss = { direction ->
            when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    isWorking = true
                    scope.launch {
                        try {
                            onSwipeRight(task.id)
                        } catch (e: Exception) {
                            isWorking = false
                            state.reset()
                        }
                    }
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    isWorking = true
                    scope.launch {
                        try {
                            onSwipeLeft(task.id)
                        } catch (e: Exception) {
                            isWorking = false
                            state.reset()
                        }
                    }
                }

                SwipeToDismissBoxValue.Settled -> {
                    /* NO-OP*/
                }
            }
        },
    ) {
        Card(
            onClick = { onClick(task.id) },
            modifier = Modifier.height(80.dp).fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.background(MaterialTheme.colorScheme.fourth).fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    Modifier.weight(3f).padding(5.dp),
                ) { // LHS
                    Text(task.title, fontWeight = FontWeight.W900)
                    Text(task.description)
                }

                val categoryModifier = Modifier.background(priorityColor(task.priority))
                Column( // RHS
                    modifier =
                        categoryModifier.fillMaxHeight().fillMaxWidth().padding(5.dp).weight(1.25f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.spoon),
                            contentDescription = "Spoon icon",
                            Modifier.size(18.dp, 18.dp),
                        )
                        Text("${task.spoons}")
                    }
                    Row {
                        Icon(
                            contentDescription = "low priority symbol",
                            painter =
                                painterResource(
                                    R.drawable.stat_2_24dp,
                                ),
                            modifier = Modifier.size(18.dp, 18.dp),
                        )
                        Text("${task.priority}")
                    }
                    Row {
                        Icon(
                            modifier = Modifier.size(18.dp, 18.dp),
                            contentDescription = "Clock Symbol",
                            painter =
                                painterResource(
                                    R.drawable.schedule_24dp,
                                ),
                        )
                        val twelveHourFormat = LocalTime.Format {
                            amPmHour(padding = Padding.ZERO)
                            char(':')
                            minute()
                            char(' ')
                            amPmMarker("AM", "PM")
                        }
                        Text(task.dueTime.format(twelveHourFormat))
                    }
                    Row {
                        Icon(
                            modifier = Modifier.size(18.dp, 18.dp),
                            contentDescription = "Calendar Symbol",
                            painter =
                                painterResource(
                                    R.drawable.calendar_month_24dp,
                                ),
                        )
                        val dateFormat = LocalDate.Format {
                            monthNumber()
                            char('/')
                            day()
                            char('/')
                            year()
                        }
                        Text(task.dueDate.format(dateFormat))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSwipeToDismissCard() {
    SwipeToDismissCard(
        {},
        onSwipeLeft = {},
        onSwipeRight = {},
        task =
            Task(
                title = "Plan The Outing",
                description = "Come up with ideas for what the friend group will do",
                spoons = 4,
                priority = Priority.High,
                category = Category.Work,
                dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                dueTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            ),
    )
}

@Suppress("MagicNumber")
@Composable
fun SwipeableTruncatedTaskCard(
    task: Task,
    swipeRightAllowed: Boolean,
    swipeLeftAllowedd: Boolean,
    onTransfer: (Long) -> Unit,
    modifier: Modifier = Modifier,
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
            Priority.Low -> Color.Gray
            Priority.Medium -> Color.White
            Priority.High -> Color.Cyan
            Priority.Critical -> Color.Red
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
                    },
                ),
    ) {
        Row( // sides go next to each other
            modifier =
                Modifier.background(
                        if (offsetX != 0f) Color.LightGray.copy(alpha = 0.3f) else color,
                    )
                    .padding(10.dp),
        ) {
            Column( // LHS
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium,
                ) // todo apply styling?
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Column( // RHS
                modifier = Modifier.weight(1f),
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
            Priority.Low -> Color.Gray
            Priority.Medium -> Color.White
            Priority.High -> Color.Cyan
            Priority.Critical -> Color.Red
        }

    Card(modifier = modifier) {
        Row( // sides go next to each other
            modifier = Modifier.background(color = color).padding(10.dp),
        ) {
            Column( // LHS
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium,
                ) // todo apply styling?
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Column( // RHS
                modifier = Modifier.weight(1f),
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
        priority = Priority.High,
        category = Category.Hygiene,
        dueDate = dueDate,
        dueTime = dueTime,
        isDone = false,
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
                isDone = i % 2 == 0,
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
                isDone = i % 2 == 0,
            )
        }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Preview of All Task Card Components") })
        },
    ) { paddingValues ->
        Row {
            LazyColumn(
                modifier = Modifier.weight(1f).padding(paddingValues),
            ) {
                items(leftList) {
                    SwipeableTruncatedTaskCard(
                        it,
                        true,
                        false,
                        { id -> leftList = leftList.filter { task -> task.id != id } },
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn(
                modifier = Modifier.weight(1f).padding(paddingValues),
            ) {
                items(rightList) { SwipeableTruncatedTaskCard(it, false, true, { id -> }) }
            }
        }
    }
}
