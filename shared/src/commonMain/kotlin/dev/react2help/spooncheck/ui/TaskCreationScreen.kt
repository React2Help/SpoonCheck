package dev.react2help.spooncheck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.TaskCreationActions
import dev.react2help.spooncheck.modelsandstate.TaskCreationUIState
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.delete_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.keyboard_arrow_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.pine_tree_background
import spooncheck.shared.generated.resources.spoon_filled
import spooncheck.shared.generated.resources.spoon_unfilled




@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskCreationScreen(OnAction: (TaskCreationActions) -> Unit, state: TaskCreationUIState) { // function that houses all UI on this screen.
    MaterialTheme{
        Scaffold(
            topBar = { // define the Header
                TopAppBar(
                    title = { Text("Create Task", fontWeight = FontWeight.Bold) },
                    subtitle = {Text("")},
                    titleHorizontalAlignment = Alignment.CenterHorizontally
                )
            },
            bottomBar = { // define the two buttons on the bottom of the screen
                BottomAppBar(
                    actions = {
                        IconButton(onClick = {
                            OnAction(TaskCreationActions.OnDelete)// todo define this callback function here
                        }){
                            Icon(
                                painter = painterResource(Res.drawable.delete_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                                contentDescription = "Delete Icon",
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                    },
                    floatingActionButton = { // RHS button with the special styling
                        FloatingActionButton(
                            onClick = {
                                OnAction(TaskCreationActions.OnSave)
                            }, // add a callback function here
                        ){
                            Icon(
                                painter = painterResource(Res.drawable.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                                contentDescription = "Check Button",
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    }
                )
            }


        ) { paddingValues ->
            Box( // use a box so the fields are stacked on top of the image

            ){
                Image(
                    painter = painterResource(Res.drawable.pine_tree_background),
                    contentDescription = "Background Image of a grove of pine trees.",
                    contentScale = ContentScale.Crop, // scale the image so it fills the screen and
                    // the parts that overflow off the screen are clipped
                    modifier = Modifier
                        .fillMaxHeight()
                )
                Column( // arrange all the fields in a column
                    verticalArrangement = Arrangement.SpaceAround, // control how the elements are
                    // placed on the Vertical axis.
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ){
                    TextField(
                        state = rememberTextFieldState(),
                        placeholder = {
                            Text("Title")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.85f)
                    )
                    TextField(
                        state = rememberTextFieldState(),
                        placeholder = {
                            Text("Description")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.85f)
                    )
                    DueDateAndNotifications()
                    SpoonSelectionCard()
                    CategoryAndPriorityCard()
                }
            }
        }
    }

}
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun TaskCreationScreen() { // function that houses all UI on this screen.
    MaterialTheme{
        Scaffold(
            topBar = { // define the Header
                TopAppBar(
                    title = { Text("Create Task", fontWeight = FontWeight.Bold) },
                    subtitle = {Text("")},
                    titleHorizontalAlignment = Alignment.CenterHorizontally
                )
            },
            bottomBar = { // define the two buttons on the bottom of the screen
                BottomAppBar(
                    actions = {
                        IconButton(onClick = {}){ // todo add a callback function here
                            Icon(
                                painter = painterResource(Res.drawable.delete_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                                contentDescription = "Delete Icon",
                                        modifier = Modifier
                                        .size(32.dp)
                            )
                        }
                    },
                    floatingActionButton = { // RHS button with the special styling
                        FloatingActionButton(
                            onClick = {}, // add a callback function here
                        ){
                            Icon(
                                painter = painterResource(Res.drawable.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                                contentDescription = "Check Button",
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    }
                )
            }


        ) { paddingValues ->
            Box( // use a box so the fields are stacked on top of the image

            ){
                Image(
                    painter = painterResource(Res.drawable.pine_tree_background),
                    contentDescription = "Background Image of a grove of pine trees.",
                    contentScale = ContentScale.Crop, // scale the image so it fills the screen and
                    // the parts that overflow off the screen are clipped
                    modifier = Modifier
                        .fillMaxHeight()
                )
                Column( // arrange all the fields in a column
                    verticalArrangement = Arrangement.SpaceBetween, // control how the elements are
                    // placed on the Vertical axis.
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ){
                    TextField(
                        state = rememberTextFieldState(),
                        placeholder = {
                            Text("Title")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.85f)
                    )
                    TextField(
                        state = rememberTextFieldState(),
                        placeholder = {
                            Text("Description")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.85f)
                    )
                    DueDateAndNotifications()
                    SpoonSelectionCard()
                    CategoryAndPriorityCard()
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
    var timeFieldState =rememberTextFieldState("")
    var dateFieldState = rememberTextFieldState("")
    Card(
modifier = Modifier
    .alpha(0.85f)
    ){
        /*
            * the elements within this card can be grouped into two groups: The switches and the
            * input fields.These groups are stacked in a column.
         */
        Column( // column to stack the two groups
            modifier = modifier
                .padding(10.dp)
        ) {
            /*
                * The switches can be thought of as a row of rows:
                * Row(Row(Text Switch) Row(Text Switch))
             */
            Row( // Wrapping Row
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    // place children next to each other inline
                    verticalAlignment = Alignment.CenterVertically,
                    //, with a little space between each
                    // other
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ){
                    Text(
                        "Notify Me"
                    )

                    Switch(
                        checked = notifySwitchIsChecked,
                        onCheckedChange = { // lambda AKA anonymous function
                            notifySwitchIsChecked = it
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        "Recurring"
                    )

                    Switch(
                        checked = recurringSwitchChecked,
                        onCheckedChange = { // lambda AKA anonymous function
                            recurringSwitchChecked = it
                        }
                    )
                }
            }
            Row { // row of text fields
                /*
                    * outputTransformation is used to automatically insert colons and forward
                    * slashes while the user types
                 */
                OutlinedTextField(
                    state = timeFieldState,
                    label = { Text("Select Due Time")},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                            ),
                            contentDescription = "Cancel Icon",

                            modifier = modifier
                                .size(18.dp) // scale the icon up so it is easily clickable
                                .clickable{// lambda AKA anonymous function
                                    timeFieldState.clearText()
                                }
                        )},

                    placeholder = {Text("HH:MM:SS")},
                    outputTransformation = OutputTransformation{// lambda AKA anonymous function
                        if(length > 2) insert(2, ":")
                        if(length > 5) insert(5, ":")
                    },
                    modifier = modifier
                        // weight() is used so each TextField attempts to occupy
                        // equal space
                        .weight(1f)
                )
                OutlinedTextField(
                    state = dateFieldState,
                    label = {Text("Select Due Date")},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                            contentDescription = "Spoon icon",
                            modifier = modifier.size(18.dp)
                                .clickable{// lambda AKA anonymous function
                                dateFieldState.clearText()
                            }
                        )},
                    placeholder = {Text("mm/dd/yy")},
                    outputTransformation = OutputTransformation{// lambda AKA anonymous function
                        if(length > 2) insert(2, "/")
                        if(length > 5) insert(5, "/")
                    },
                    modifier = modifier
                        // weight() is used so each TextField attempts to occupy
                        // equal space
                        .weight(1f)
                )
            }
        }
    }
}



@Preview
@Composable
fun SpoonSelectionCard(modifier: Modifier = Modifier){
    /*
    Potential Issues
     - THe clickable surface is too small and cumbersome to use effectively. CInsider replacing with
      a Surface.
      - The spoon icons need to be replaced to match the Figma
     */

    val maxSpoons = 5
    var selectedSpoons by remember {mutableIntStateOf(2)}
    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(0.85f)
    ){
        Column(
            modifier = modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text("Spoons Required")
            Row( // how to center this in the card:
                                                    // order of operations matters with modifiers!!!
                modifier = modifier.fillMaxWidth(), // give the element the max available width
                horizontalArrangement = Arrangement.Center, // center it along the horizontal axis
                verticalAlignment = Alignment.CenterVertically // center it along the vertical axis
            ){
                for (i in 1..maxSpoons){ // a for loop creates icons programmatically instead
                    // of specifying 5 Icons
                    if(i <= selectedSpoons){
                        Icon(
                            painter = painterResource(Res.drawable.spoon_filled),
                            contentDescription = "Unfilled Spoon Icon",
                            modifier = modifier
                                .size(64.dp)
                                .clickable{
                                    selectedSpoons = i
                                }
                        )
                    }
                    else{
                        Icon(
                            painter = painterResource(Res.drawable.spoon_unfilled),
                            contentDescription = "Filled Spoon Icon",
                            modifier = modifier
                                .size(64.dp)
                                .clickable {
                                    selectedSpoons = i
                                }
                        )
                    }

                }
            }

        }
    }
}



@Preview
@Composable
fun CategoryAndPriorityCard(modifier: Modifier = Modifier) {
    val menuItems = listOf<String>("Hygiene", "Household", "Work", "Recreation", "Social", "Pets")
    var selectedOption by remember {mutableIntStateOf(0)}
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.85f)
    ){

        Column(
            verticalArrangement = Arrangement.SpaceAround
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Category")
                Row(
                    verticalAlignment = Alignment.CenterVertically, // make elements inline with
                    // each other
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(menuItems.get(selectedOption)) // show which option was selected
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = { expanded = !expanded }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.keyboard_arrow_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                                contentDescription = "Category Drop Down Menu Icon"
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            menuItems.forEachIndexed { index, option ->
                                DropdownMenuItem(
                                    text = {
                                        Text(option)
                                    },
                                    onClick = {
                                        selectedOption = index
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Priority Level")
                PrioritySelectButton()
            }
        }

    }
}
@Composable
fun PrioritySelectButton(modifier: Modifier = Modifier){
    var selectedIndex by remember {mutableIntStateOf(0)}
    val options = listOf("low", "medium", "high", "critical")
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {selectedIndex = index}, // this callback function should change the value
                // in the view model instead, but for now I'm leaving that until it's time to
                // refactor
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}