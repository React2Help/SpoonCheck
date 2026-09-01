package dev.react2help.spooncheck.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.viewmodels.TaskListViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.arrow_drop_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.arrow_drop_up_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.logo
import spooncheck.shared.generated.resources.ocean_view
import kotlin.time.Clock


@Composable
fun TaskListScreen(viewModel: TaskListViewModel){
    var selected by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Task List")
                },
                actions = {
                    InputChip(
                        selected = selected,
                        onClick = {
                            selected =
                                !selected // todo replace this with call to onAction with SpoonChip
                            // Pressed Action passed in
                        },
                        shape = CircleShape,
                        label = { Text("5 spoons") } // todo extract number to viewModel
                    )
                }
            )
        },
        bottomBar = {

        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {

        }
    ) { paddingValues ->

    }

}
@Preview
@Composable
fun TaskListScreen(){
    var selected by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Task List")
                },
                actions = {
                    InputChip( // spoon selection button
                        selected = selected,
                        onClick = {
                            selected =
                                !selected // todo replace this with call to onAction with SpoonChip
                            // Pressed Action passed in
                        },
                        shape = CircleShape,
                        label = { Text("5 spoons") } // todo extract number to viewModel
                    )
                    IconButton( // account button
                        onClick = {}
                    ){
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
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { println() }, // todo pass in navigation functions
                    icon = {},
                    label = { Text("Dashboard") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {}, // todo pass in navigation functions
                    icon = {},
                    label = { Text("Tasks") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {}, // todo pass in navigation functions
                    icon = {},
                    label = { Text("Patterns") }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            IconButton(
                onClick = { // todo

                },
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo"
                )
            }
        }
    ) { paddingValues ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize()){
            val boxWithConstraintsScope = this
            Image(
                imageResource(
                    Res.drawable.ocean_view
                ),
                contentDescription = "Background image",
                        contentScale = ContentScale.Crop, // scale the image so it fills the screen and
                // the parts that overflow off the screen are clipped
                modifier = Modifier
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),

                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TaskStatusFilter()
                Spacer(modifier = Modifier.size(8.dp))
                Card(
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier
                        .widthIn(max = boxWithConstraintsScope.maxWidth * 0.9f)
                        .heightIn(min = boxWithConstraintsScope.maxHeight * 0.25f)
                ) {
                    val tasks :List<Task> = List(8) { i ->
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

                    val sectionDatii = List(Category.entries.size) { i ->
                        SectionData(
                            header = Category.entries.get(i).name,
                            tasks = tasks.filter { task ->
                                task.category == Category.entries.get(i)
                            }
                        )
                    }
                    ExpandableList(sectionDatii)

                }
            }
        }

    }

}

@Composable
fun SectionItem(
    task: Task,
    modifier: Modifier = Modifier
){
    Card(
        modifier = Modifier
            .background(Color(0xFFf6feff))
            .height(80.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xfff6feff))
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Column(
                Modifier.weight(3f)

                    .padding(5.dp)
            ) { // LHS
                Text(
                    task.title,
                    fontWeight = FontWeight.W900
                )
                Text(
                    task.description
                )
            }

            val categoryModifier = when(task.priority){
                Priority.critical -> modifier.background(Color(0xffd4e2e3))
                Priority.high -> modifier.background(Color(0xFFEAD3B6))
                Priority.medium -> modifier.background(Color(0xFFfef2dc))
                Priority.low -> modifier.background(Color(0xFFfef2dc))
            }
            Column( // RHS

                modifier = categoryModifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(5.dp)
                    .weight(1.25f),
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
                        painter = painterResource(Res.drawable.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                        modifier = Modifier.size(18.dp, 18.dp)
                    )
                    Text("${task.priority}")
                }
                Row {
                    Icon(
                        modifier = modifier.size(18.dp, 18.dp),
                        contentDescription = "Clock Symbol",
                        painter = painterResource(Res.drawable.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
                    )
                    val twelveHourFormat = LocalTime.Format{
                        amPmHour(padding = Padding.ZERO)
                        char(':')
                        minute()
                        char(' ')
                        amPmMarker("AM", "PM")
                    }
                    Text(task.due_time.format(twelveHourFormat))
                }
                Row {
                    Icon(
                        modifier = modifier.size(18.dp, 18.dp),
                        contentDescription = "Calendar Symbol",
                        painter = painterResource(Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
                    )
                    val dateFormat = LocalDate.Format {
                        monthNumber()
                        char('/')
                        day()
                        char('/')
                        year()
                    }
                    Text(task.due_date.format(dateFormat))
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskCard(){
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 80.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            Column(
                Modifier.weight(3f)
                    .padding(5.dp)
            ) { // LHS
                Text(
                    "Title",
                    fontWeight = FontWeight.W900
                )
                Text(
                    "Description with even more things to do. More text that lowkey means nothing...",
                )
            }
            Column( // RHS
                Modifier.background(Color.LightGray)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(5.dp)
                    .weight(1.25f),
                horizontalAlignment = Alignment.End
            ) {
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.spoon),
                        contentDescription = "Spoon icon",
                        Modifier.size(18.dp, 18.dp)
                    )
                    Text("5")
                }
                Row {
                    Icon(

                        contentDescription = "low priority symbol",
                        painter = painterResource(Res.drawable.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                        modifier = Modifier.size(18.dp, 18.dp)
                    )
                    Text("High")
                }
                Row {
                    Icon(
                        modifier = Modifier.size(18.dp, 18.dp),
                        contentDescription = "Clock Symbol",
                        painter = painterResource(Res.drawable.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
                    )
                    Text("12:30PM")
                }
                Row {
                    Icon(
                        modifier = Modifier.size(18.dp, 18.dp),
                        contentDescription = "Calendar Symbol",
                        painter = painterResource(Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
                    )
                    Text("06/07/2026")
                }
            }
        }

    }

}

@Preview
@Composable
fun TaskStatusFilter(){
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("All Tasks", "Todo", "Done")
    SingleChoiceSegmentedButtonRow(

    ) { options.forEachIndexed { index, label ->
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = index,
                count = options.size
            ),
            onClick = {selectedIndex = index},
            selected = index == selectedIndex,
            label = {Text(label)},
            colors = SegmentedButtonColors(
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

@Composable
fun SectionHeader(text : String = "Important", isExpanded: Boolean = false, onHeaderClicked: () -> Unit){
    // A Component. A Horizontal element with a "dropdown" chevron, a label, and a plus icon


    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton( // drop down icon
            onClick = { onHeaderClicked()},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF5D82A2),
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            ),

        ){
            Icon(
                painter = if (isExpanded) painterResource(Res.drawable.arrow_drop_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24) else painterResource(Res.drawable.arrow_drop_up_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                contentDescription = if (isExpanded) "drop down icon" else "drop up icon",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = text,
            color = Color(0xFF5D82A2),
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.size(50.dp))

    }

}
@Composable
fun ExpandableList(sections: List<SectionData>) {
    val expandedMapSaver =
        listSaver<SnapshotStateMap<Int, Boolean>, Boolean>(
            save = { map ->
                sections.indices.map { index ->
                    map[index] ?: true
                }
            },
            restore = { savedValues ->
                savedValues
                    .mapIndexed { index, isExpanded ->
                        index to isExpanded
                    }
                    .toMutableStateMap()
            }
        )

    val isExpandedMap = rememberSaveable(
        sections.size,
        saver = expandedMapSaver
    ) {
        sections.indices
            .map { index -> index to true }
            .toMutableStateMap()
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        sections.forEachIndexed { index, sectionData ->
            Section(
                sectionData = sectionData,
                isExpanded = isExpandedMap[index] ?: true,
                onHeaderClick = {
                    isExpandedMap[index] =
                        !(isExpandedMap[index] ?: true)
                }
            )
        }
    }
}
fun LazyListScope.Section(
    sectionData: SectionData,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit
) {

    item {
        SectionHeader(
            text = sectionData.header,
            isExpanded = isExpanded,
            onHeaderClicked = onHeaderClick
        )
    }
    if(isExpanded){
        items(sectionData.tasks){
            SectionItem(task = it)
        }
    }

}

@Preview
@Composable
fun NewTaskButton(){
    var clicked by remember {mutableStateOf(false)}

    IconButton(
        onClick =  { clicked = !clicked},
        shape = CircleShape,
        modifier = Modifier
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    4.dp
                )
            )
    ){
        Icon(
            painter = painterResource(Res.drawable.spoon),
            contentDescription = "Spoon Icon Button"
        )
    }
}
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


