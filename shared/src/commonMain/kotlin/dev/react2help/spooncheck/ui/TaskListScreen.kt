@file:Suppress(
    "LongMethod",
    "MagicNumber",
    "ModifierMissing",
    "ModifierNotUsedAtRoot",
    "ModifierReused",
    "PreviewPublic",
)

package dev.react2help.spooncheck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.AppTheme
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.modelsandstate.TaskListActions
import dev.react2help.spooncheck.modelsandstate.TaskListFilterOptions
import dev.react2help.spooncheck.theme.LocalAppTheme
import dev.react2help.spooncheck.theme.fourth
import dev.react2help.spooncheck.ui.components.EmptyTaskListComponent
import dev.react2help.spooncheck.viewmodels.TaskListViewModel
import kotlin.math.roundToInt
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.arrow_drop_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.arrow_drop_up_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.logo
import spooncheck.shared.generated.resources.ocean_view
import spooncheck.shared.generated.resources.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24

@Composable
fun TaskListScreen(viewModel: TaskListViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var selected by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task List") },
                actions = {
                    InputChip( // spoon selection button todo replace with adjustable spoon meter
                        selected = selected,
                        onClick = {
                            selected =
                                !selected // todo replace this with call to onAction with SpoonChip
                            // Pressed Action passed in
                        },
                        shape = CircleShape,
                        label = {
                            Text("${uiState.spoonBudget} spoons")
                        } // todo extract number to viewModel
                    )
                    IconButton( // account button
                        onClick = { viewModel.OnAction(TaskListActions.NavigateToAccount) }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.logo), // todo figure out why
                            // todo it does not render
                            contentDescription = "Logo"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar { // todo extract state of selected
                NavigationBarItem(
                    selected = false,
                    onClick = { viewModel.OnAction(TaskListActions.NavigateToDashboard) },
                    icon = {},
                    label = { Text("Dashboard") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { viewModel.OnAction(TaskListActions.NavigateToTaskList) },
                    icon = {},
                    label = { Text("Tasks") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { viewModel.OnAction(TaskListActions.NavigateToPatterns) },
                    icon = {},
                    label = { Text("Patterns") }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            IconButton(
                onClick = { viewModel.OnAction(TaskListActions.NavigateToTaskCreation) },
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(painter = painterResource(Res.drawable.logo), contentDescription = "Logo")
            }
        }
    ) { paddingValues ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val boxWithConstraintsScope = this
            Image(
                imageResource(Res.drawable.ocean_view),
                contentDescription = "Background image",
                contentScale = ContentScale.Crop, // scale the image so it fills the screen and
                // the parts that overflow off the screen are clipped
                modifier = Modifier.fillMaxHeight()
            )
            if (uiState.tasks.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TaskStatusFilter(
                        uiState.listFilterOption,
                        { selectedOption: TaskListFilterOptions ->
                            viewModel.OnAction(TaskListActions.onFilterOptionChange(selectedOption))
                        }
                    ) // todo expose fields to view model and set OnAction
                    Spacer(modifier = Modifier.size(8.dp))
                    Card(
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier =
                            Modifier.widthIn(max = boxWithConstraintsScope.maxWidth * 0.9f)
                                .heightIn(min = boxWithConstraintsScope.maxHeight * 0.25f)
                    ) {
                        val sectionDatii =
                            List(Category.entries.size) { i ->
                                SectionData(
                                    header = Category.entries[i].name,
                                    tasks =
                                        uiState.tasks.filter { task ->
                                            task.category == Category.entries[i]
                                        }
                                )
                            }
                        ExpandableList(
                            sectionDatii,
                            onSwipeLeft = { taskId ->
                                viewModel.OnAction(TaskListActions.OnDelete(taskId))
                            },
                            onSwipeRight = { taskId ->
                                viewModel.OnAction(TaskListActions.CompletionChanged(taskId, true))
                            },
                            { taskId ->
                                viewModel.OnAction(
                                    TaskListActions.NavigateToTaskDetails(taskId = taskId)
                                )
                            }
                        )
                    }
                }
            } else {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxSize()
                ) {
                    TaskStatusFilter(
                        uiState.listFilterOption,
                        { selectedOption: TaskListFilterOptions ->
                            viewModel.OnAction(TaskListActions.onFilterOptionChange(selectedOption))
                        }
                    )
                    EmptyTaskListComponent(paddingValues)
                }
            }
        }
    }
}

/*
   ------------------------------------------------------------------------------------------------

*/
@Composable // todo write preview function
fun TaskStatusFilter(
    selectedFilter: TaskListFilterOptions,
    onFilterOptionChange: (TaskListFilterOptions) -> Unit
) {
    val options = listOf("All Tasks", "Todo", "Done")
    SingleChoiceSegmentedButtonRow {
        TaskListFilterOptions.entries.forEachIndexed { index, option ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onFilterOptionChange(TaskListFilterOptions.entries[index]) },
                selected = index == selectedFilter.ordinal,
                label = {
                    Text(
                        option.name.toLowerCase(Locale.current).replaceFirstChar { ch ->
                            ch.uppercase()
                        }
                    )
                },
                colors =
                    SegmentedButtonColors(
                        activeContainerColor = Color(0xFF27567D),
                        activeContentColor = Color(0xFFD0D7DB),
                        activeBorderColor = MaterialTheme.colorScheme.outline,
                        inactiveContainerColor = Color(0xFFD0D7DB),
                        inactiveContentColor = Color(0xFF27567D),
                        inactiveBorderColor = MaterialTheme.colorScheme.outline,
                        disabledActiveContainerColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledActiveContentColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledActiveBorderColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledInactiveContainerColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledInactiveContentColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledInactiveBorderColor = MaterialTheme.colorScheme.surfaceDim,
                    )
            )
        }
    }
}

/*
----------------------------------------------------------------------------------------------------
    Helper Functions for the Expandable List component


----------------------------------------------------------------------------------------------------
 */
@Composable
fun ExpandableList(
    sections: List<SectionData>,
    onSwipeLeft: (Long) -> Unit,
    onSwipeRight: (Long) -> Unit,
    onCardClick: (Long) -> Unit
) {
    val expandedMapSaver =
        listSaver<SnapshotStateMap<Int, Boolean>, Boolean>(
            save = { map -> sections.indices.map { index -> map[index] ?: true } },
            restore = { savedValues ->
                savedValues
                    .mapIndexed { index, isExpanded -> index to isExpanded }
                    .toMutableStateMap()
            }
        )

    val isExpandedMap =
        rememberSaveable(sections.size, saver = expandedMapSaver) {
            sections.indices.map { index -> index to true }.toMutableStateMap()
        }

    LazyColumn(modifier = Modifier.padding(8.dp).background(Color.White)) {
        sections.forEachIndexed { index, sectionData ->
            Section(
                sectionData = sectionData,
                isExpanded = isExpandedMap[index] ?: true,
                onHeaderClick = { isExpandedMap[index] = !(isExpandedMap[index] ?: true) },
                onCardClick = onCardClick,
                onSwipeLeft = onSwipeLeft,
                onSwipeRight = onSwipeRight
            )
        }
    }
}

@Composable
fun SectionHeader(
    text: String = "Important",
    isExpanded: Boolean = false,
    onHeaderClick: () -> Unit
) {
    // A Component. A Horizontal element with a "dropdown" chevron, a label, and a plus icon

    Row(
        Modifier.fillMaxWidth().background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton( // drop down icon
            onClick = { onHeaderClick() },
            colors =
                IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF5D82A2),
                    disabledContentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified
                ),
        ) {
            Icon(
                painter =
                    if (isExpanded)
                        painterResource(
                            Res.drawable.arrow_drop_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                        )
                    else
                        painterResource(
                            Res.drawable.arrow_drop_up_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                        ),
                contentDescription = if (isExpanded) "drop down icon" else "drop up icon",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(text = text, color = Color(0xFF5D82A2), style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.size(50.dp))
    }
}

@Composable
fun SectionItem(
    task: Task,
    onCardClick: (Long) -> Unit,
    onSwipeLeft: (Long) -> Unit,
    onSwipeRight: (Long) -> Unit,
    modifier: Modifier = Modifier,
) { // task card
    var offsetX by remember { mutableFloatStateOf(0f) }
    val dragThreshold = 120f
    Card(
        onClick = { onCardClick(task.id) },
        modifier =
            Modifier.offset { IntOffset(offsetX.roundToInt(), 0) }
                .background(Color(0xFFf6feff))
                .height(80.dp)
                .fillMaxWidth()
                .draggable(
                    state = rememberDraggableState { delta -> offsetX += delta },
                    orientation = Orientation.Horizontal,
                    onDragStopped = { f ->
                        if (f > dragThreshold) onSwipeRight(task.id)
                        if (f < -dragThreshold) onSwipeLeft(task.id)
                        offsetX = 0f
                    }
                ),
    ) {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.fourth).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(Modifier.weight(3f).padding(5.dp)) { // LHS
                Text(task.title, fontWeight = FontWeight.W900)
                Text(task.description)
            }

            val categoryModifier = modifier.background(priorityColor(task.priority))
            Column( // RHS
                modifier =
                    categoryModifier.fillMaxHeight().fillMaxWidth().padding(5.dp).weight(1.25f),
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.spoon),
                        contentDescription = "Spoon icon",
                        Modifier.size(18.dp, 18.dp)
                    )
                    Text("${task.spoons}")
                }
                Row {
                    Icon(
                        contentDescription = "low priority symbol",
                        painter =
                            painterResource(
                                Res.drawable.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                            ),
                        modifier = Modifier.size(18.dp, 18.dp)
                    )
                    Text("${task.priority}")
                }
                Row {
                    Icon(
                        modifier = modifier.size(18.dp, 18.dp),
                        contentDescription = "Clock Symbol",
                        painter =
                            painterResource(
                                Res.drawable.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                            )
                    )
                    val twelveHourFormat =
                        LocalTime.Format {
                            amPmHour(padding = Padding.ZERO)
                            char(':')
                            minute()
                            char(' ')
                            amPmMarker("AM", "PM")
                        }
                    Text(task.dueTime?.format(twelveHourFormat) ?: "--:--")
                }
                Row {
                    Icon(
                        modifier = modifier.size(18.dp, 18.dp),
                        contentDescription = "Calendar Symbol",
                        painter =
                            painterResource(
                                Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                            )
                    )
                    val dateFormat =
                        LocalDate.Format {
                            monthNumber()
                            char('/')
                            day()
                            char('/')
                            year()
                        }
                    Text(task.dueDate?.format(dateFormat) ?: "--/--/--")
                }
            }
        }
    }
}

fun LazyListScope.Section(
    sectionData: SectionData,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit,
    onSwipeLeft: (Long) -> Unit,
    onSwipeRight: (Long) -> Unit,
    onCardClick: (Long) -> Unit
) {

    item {
        SectionHeader(
            text = sectionData.header,
            isExpanded = isExpanded,
            onHeaderClick = onHeaderClick
        )
    }
    if (isExpanded) {
        items(sectionData.tasks) { task ->
            SectionItem(
                task = task,
                onCardClick = onCardClick,
                onSwipeLeft = onSwipeLeft,
                onSwipeRight = onSwipeRight
            )
        }
    }
}
// Priority colors on the task cards. They mean the same thing in every theme, so Forest and Beach
// share them; Dark gets darker versions so the light text on top stays readable.
@Composable
private fun priorityColor(priority: Priority): Color {
    val isDark = LocalAppTheme.current == AppTheme.DARK
    return when (priority) {
        Priority.critical -> if (isDark) Color(0xFF2F4A50) else Color(0xFFD4E2E3)
        Priority.high -> if (isDark) Color(0xFF5A4632) else Color(0xFFEAD3B6)
        Priority.medium,
        Priority.low -> if (isDark) Color(0xFF4A4232) else Color(0xFFFEF2DC)
    }
}

// Previews don't run App.kt, so they wrap the screen in SpoonCheckTheme themselves.
// By Ryan: Because they don't run app.kt they don't receive their dependencies. Commenting them out
// until we can figure out a decent way to wire in a viewmodel.
/*
@Preview
@Composable
private fun TaskListForestPreview() {
    SpoonCheckTheme(appTheme = AppTheme.FOREST) { TaskListScreen() }
}

@Preview
@Composable
private fun TaskListBeachPreview() {
    SpoonCheckTheme(appTheme = AppTheme.BEACH) { TaskListScreen() }
}

@Preview
@Composable
private fun TaskListDarkPreview() {
    SpoonCheckTheme(appTheme = AppTheme.DARK) { TaskListScreen() }
}

 */

/*
@Preview
@Composable
fun TaskList(tasks :List<Task> = List(8) { i ->
     Task(
        "Task $i",
        "description $i",
        i,
        priority = Priority.entries.get(i % Priority.entries.size),
         Category.entries.get(i % Category.entries.size),
        Clock.System.now().toLocalDateTime(
            TimeZone.currentSystemDefault()
        ).date,
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    )
}
) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .padding(16.dp),


    ){
        Column(
            Modifier
                .background(Color.White)
        ){
            for (category in Category.entries){
                SectionHeader(category.toString())
                val  tasksInCategory: List<Task> = tasks.filter { category == it.category }
                for (task in tasksInCategory){
                    SectionItem(task)
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
        }
    }
}
 */

data class SectionData(val header: String, val tasks: List<Task>)
