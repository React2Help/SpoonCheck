package dev.react2help.spooncheck.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.viewmodels.TaskListViewModel
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.add_circle_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.arrow_drop_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.arrow_drop_up_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24


@Composable
fun TaskListScreen(viewModel: TaskListViewModel){
    Scaffold(
        topBar = {

        },
        bottomBar = {

        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {

        }
    ) { paddingValues ->  }

}

@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier
){
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
                    task.title,
                    fontWeight = FontWeight.W900
                )
                Text(
                    task.description
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
                        modifier = Modifier.size(18.dp, 18.dp),
                        contentDescription = "Clock Symbol",
                        painter = painterResource(Res.drawable.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
                    )
                    Text("${task.due_time}")
                }
                Row {
                    Icon(
                        modifier = Modifier.size(18.dp, 18.dp),
                        contentDescription = "Calendar Symbol",
                        painter = painterResource(Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
                    )
                    Text("${task.due_date}")
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
    Card {
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

}

@Preview
@Composable
fun DropdownSectionButton(){
    // A Component. A Horizontal element with a "dropdown" chevron, a label, and a plus icon
    var isToggled by remember {mutableStateOf(true)}
    var addItem by remember { mutableStateOf(false)}

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton( // drop down icon
            onClick = { isToggled = !isToggled},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF5D82A2),
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            ),

        ){
            Icon(
                painter = if (isToggled) painterResource(Res.drawable.arrow_drop_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24) else painterResource(Res.drawable.arrow_drop_up_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                contentDescription = if (isToggled) "drop down icon" else "drop up icon",
                
            )
        }
        Text("Important (2)", color = Color(0xFF5D82A2))
        Spacer(Modifier.size(50.dp))
        IconButton(
            onClick = { addItem = !addItem},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF5D82A2),
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            )
        ){
            Icon (
                painter = painterResource(Res.drawable.add_circle_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                contentDescription = "Add Icon"
            )
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

