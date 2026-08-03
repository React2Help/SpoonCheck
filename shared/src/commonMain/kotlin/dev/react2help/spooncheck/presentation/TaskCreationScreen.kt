package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.spoon_filled
import spooncheck.shared.generated.resources.spoon_unfilled

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun TaskCreationScreen() {
    MaterialTheme{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Create Task", fontWeight = FontWeight.Bold) },
                    subtitle = {Text("")},
                    titleHorizontalAlignment = Alignment.CenterHorizontally
                )
            }
        ) { paddingValues ->
            Box(

            ){
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ){
                    TextField(
                        state = rememberTextFieldState(),
                        placeholder = {
                            Text("Title")
                        },
                        label = {
                            Text("Hello World!")
                        }
                    )
                    TextField(
                        state = rememberTextFieldState(),
                        placeholder = {
                            Text("Description")
                        }
                    )
                    DueDateAndNotifications()
                }
            }
        }
    }

}

@Composable
fun PrioritySelectButton(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("low", "medium", "high", "critical")
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}
@Preview
@Composable
fun DueDateAndNotifications(modifier: Modifier = Modifier){
    var notifySwitchIsChecked by remember {mutableStateOf(true)}
    var recurringSwitchChecked by remember {mutableStateOf(true)}
    Card{
        Column {
            Row {
                Text(
                    "Notify Me"
                )
                Switch(
                    checked = notifySwitchIsChecked,
                    onCheckedChange = {
                        notifySwitchIsChecked = it
                    }
                )
                Text(
                    "Recurring"
                )
                Switch(
                    checked = recurringSwitchChecked,
                    onCheckedChange = {
                        recurringSwitchChecked = it
                    }
                )
            }
            Row {
                OutlinedTextField(
                    state = rememberTextFieldState(),
                    label = { Text("Select Due Time")},
                    placeholder = {Text("HH:MM:SS")}
                )
                OutlinedTextField(
                    state = rememberTextFieldState(),
                    label = {Text("Select Due Date")},
                    placeholder = {Text("mm/dd/yy")}
                )
            }
        }
    }
}
@Preview
@Composable
fun SpoonSelectionCard(modifier: Modifier = Modifier){
    val maxSpoons = 5
    var selectedSpoons by remember {mutableIntStateOf(3)}
    Card(

    ){
        Column { 
            Text("Spoons Required")
            Row{
                for (i in 1..maxSpoons){
                    if (i < selectedSpoons){
                        Icon(painter = painterResource(Res.drawable.spoon_filled), contentDescription = "Spoon icon", modifier = Modifier.size(24.dp))
                    }else {
                        Icon(painter = painterResource(Res.drawable.spoon_unfilled), contentDescription = "Spoon Icon", modifier = Modifier.size(24.dp))
                    }
                }
            }

        }
    }
}