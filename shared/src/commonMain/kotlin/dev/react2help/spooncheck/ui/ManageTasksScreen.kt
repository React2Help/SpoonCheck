package dev.react2help.spooncheck.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.ui.components.SwipeableTruncatedTaskCard
import dev.react2help.spooncheck.utils.generateRandomFutureDate
import dev.react2help.spooncheck.utils.plusHoursSimple
import kotlin.math.abs
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

/* This screen is meant to be part 2 of the Morning Checkin sequence.
   ViewModel needs to track:
    - Two lists
*/
@Suppress("LongMethod")
@Composable
fun ManageTasksScreen(
    futureTasksAsConst: List<Task>,
    todayTasksAsConst: List<Task>,
    modifier: Modifier = Modifier
) {
    /*
    When the time comes, Refactor callbacks and list declarations to a viewmodel
     */
    var futureTasks by remember(futureTasksAsConst) { mutableStateOf(futureTasksAsConst) }
    var todayTasks by remember(todayTasksAsConst) { mutableStateOf(todayTasksAsConst) }
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val tomorrow = today.plus(1, DateTimeUnit.DAY)

    fun transferTask(
        taskId: Long,
        source: List<Task>,
        destination: List<Task>,
        newDueDate: LocalDate,
        updateSource: (List<Task>) -> Unit,
        updateDestination: (List<Task>) -> Unit
    ) {
        val task = source.firstOrNull { it.id == taskId } ?: return
        updateSource(source.filterNot { it.id == task.id })

        updateDestination((destination + task.copy(dueDate = newDueDate)).distinctBy { it.id })
    }

    val onFutureTaskTransferred: (Long) -> Unit = { taskId ->
        transferTask(
            taskId = taskId,
            source = futureTasks,
            destination = todayTasks,
            newDueDate = today,
            updateSource = { futureTasks = it },
            updateDestination = { todayTasks = it },
        )
    }

    val onTodayTaskTransferred: (Long) -> Unit = { taskId ->
        transferTask(
            taskId = taskId,
            source = todayTasks,
            destination = futureTasks,
            newDueDate = tomorrow,
            updateSource = { todayTasks = it },
            updateDestination = { futureTasks = it },
        )
    }
    Scaffold(
        topBar = { CenterAlignedTopAppBar({ Text("Manage Today's Tasks") }) },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { /*TODO*/}) { Text("Skip") }
                    Button(onClick = { /* TODO */}) { Text("Save") }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            Card {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxHeight().padding(horizontal = 2.dp, vertical = 0.dp)
                ) {
                    Text(
                        "Drag tasks you would prefer to schedule" +
                            " for today from the left to the right. " +
                            "Likewise, drag tasks you would prefer to postpone for a later date" +
                            " from the right to the left.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Future Tasks", style = MaterialTheme.typography.titleMedium)
                        Text("Today's To-do list", style = MaterialTheme.typography.titleMedium)
                    }
                    Row(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(futureTasksAsConst) { task ->
                                SwipeableTruncatedTaskCard(
                                    task,
                                    true,
                                    false,
                                    onFutureTaskTransferred
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(todayTasksAsConst) { task ->
                                SwipeableTruncatedTaskCard(
                                    task,
                                    true,
                                    false,
                                    onTodayTaskTransferred
                                )
                            }
                        }
                    }
                }
            }

            // ListOfCardsWithTaskCards(sections)

        }
    }
}

/*
Everything below here facilitates the preview
 */
@Suppress("MagicNumber")
@Preview
@Composable
private fun PreviewScreen() {

    // retreive the list of tasks. Group by date and category
    val tasks =
        List(20) { i ->
            Task(
                id = Uuid.random().toLongs { mostSignificantBits, _ -> abs(mostSignificantBits) },
                title = "Task #${i + 1}",
                isDone = i % 2 == 0,
                description = "Description for task ${i}.${i}",
                spoons = (i + 1) % 10,
                priority = Priority.entries[i % Priority.entries.size],
                category = Category.entries[i % Category.entries.size],
                dueDate =
                    if (i % 2 == 0) generateRandomFutureDate()
                    else Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                dueTime =
                    Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .time
                        .plusHoursSimple((5 + i) % 12),
            )
        }

    val rightList =
        tasks.filter { task ->
            task.dueDate?.equals(
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            ) == true
        }
    val leftList =
        tasks.filter { task -> // todo change logic. Make this function take future
            // todo not future + past
            task.dueDate?.equals(
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            ) != true
        }

    ManageTasksScreen(leftList, rightList)
}
