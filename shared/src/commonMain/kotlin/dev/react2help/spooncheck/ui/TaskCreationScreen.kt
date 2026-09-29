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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.isPm
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.TaskCreationActions
import dev.react2help.spooncheck.modelsandstate.max_spoons
import dev.react2help.spooncheck.utils.TextToDate
import dev.react2help.spooncheck.viewmodels.TaskCreationViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.delete_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.pine_tree_background
import spooncheck.shared.generated.resources.spoon_filled
import spooncheck.shared.generated.resources.spoon_unfilled
import kotlin.time.Clock

// true if 4 digit entry is a valid HH:MM time
fun isValidTime(raw: String): Boolean {
    if (raw.length != 4) return false
    val h = raw.substring(0, 2).toIntOrNull()
    val m = raw.substring(2, 4).toIntOrNull()
    return h != null && m != null && h in 0..23 && m in 0..59
}

// true if the 6 digit entry is a valid mm/dd/yy date
fun isValidDate(raw: String): Boolean {
    if (raw.length != 6) return false
    val month = raw.substring(0, 2).toIntOrNull()
    val day = raw.substring(2, 4).toIntOrNull()
    val year = raw.substring(4, 6).toIntOrNull()
    return month != null &&
        day != null &&
        year != null &&
        runCatching { LocalDate(year + 2000, month, day) }.isSuccess
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskCreationScreenGen(
    viewModel: TaskCreationViewModel,
    isDateFieldEnabled: Boolean = false,
) { // function that houses all UI on this screen.
    val uiState by viewModel.uiState.collectAsState()
    // error flags when save is pressed with invalid input

    MaterialTheme {
        Scaffold(
            topBar = { // define the Header
                TopAppBar(
                    title = { Text("Create Task", fontWeight = FontWeight.Bold) },
                    subtitle = { Text("") },
                    titleHorizontalAlignment = Alignment.CenterHorizontally
                )
            },
            bottomBar = { // define the two buttons on the bottom of the screen
                BottomAppBar(
                    actions = {
                        IconButton(
                            onClick = { viewModel.onAction(TaskCreationActions.Cancel) }
                        ) { // todo add a callback function here
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.delete_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Delete Icon",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    floatingActionButton = { // RHS button with the special styling
                        FloatingActionButton(
                            onClick = {
                                /*
                                TODO validate time. Deleted old validation logic because it didn't fit with the new composable.
                                 */
                                // validate all fields before saving
                                val isTitleOk = uiState.titleFieldState.text.isNotBlank()
                                val dateCheck = isValidDate(uiState.dateFieldState.text.toString())
                                val isDateOk = !isDateFieldEnabled || dateCheck

                                viewModel.onAction(TaskCreationActions.SetTitleError(!isTitleOk))
                                viewModel.onAction(
                                    TaskCreationActions.SetDateError(
                                        isDateFieldEnabled && !isDateOk
                                    )
                                )

                                if (isTitleOk && isDateOk) {
                                    val task =
                                        Task(
                                            id = 0, // TODO: Create function for making IDs
                                            title = uiState.titleFieldState.text.toString(),
                                            description =
                                                uiState.descriptionFieldState.text.toString(),
                                            priority = uiState.priority,
                                            category = uiState.category,
                                            dueDate =
                                                TextToDate(uiState.dateFieldState.text.toString()),
                                            dueTime = LocalTime(
                                                hour = uiState.timePickerState.hour,
                                                minute = uiState.timePickerState.minute
                                            ),
                                            spoons = uiState.spoons
                                        )
                                    viewModel.onAction(TaskCreationActions.Save(task))
                                }
                            },
                            containerColor = Color(0xFF7799A4), // todo extract out to theme
                            contentColor = Color(0xFFFFFFFF),
                        ) {
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Save Task",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box( // use a box so the fields are stacked on top of the image
            ) {
                Image(
                    painter = painterResource(Res.drawable.pine_tree_background),
                    contentDescription = "Background Image of a grove of pine trees.",
                    contentScale = ContentScale.Crop, // scale the image so it fills the screen and
                    // the parts that overflow off the screen are clipped
                    modifier = Modifier.fillMaxHeight()
                )
                Column( // arrange all the fields in a column
                    verticalArrangement =
                        Arrangement.spacedBy(8.dp), // control how the elements are
                    // placed on the Vertical axis (now set for scrolling).
                    modifier =
                        Modifier.fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValues)
                ) {
                    TextField(
                        state = uiState.titleFieldState,
                        placeholder = { Text("Title") },
                        isError = uiState.titleError,
                        supportingText = {
                            if (uiState.titleError)
                                Text(
                                    "Title cannot be empty",
                                    // color = Color(0xFF531E1E),
                                    modifier =
                                        Modifier.background(Color(0xFFFFFFFF))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                        },
                        trailingIcon = {
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Clear title",
                                modifier =
                                    Modifier.size(18.dp).clickable {
                                        uiState.titleFieldState.clearText()
                                        viewModel.onAction(TaskCreationActions.SetTitleError(false))
                                    },
                            )
                        },
                        modifier =
                            Modifier.fillMaxWidth().onFocusChanged {
                                if (it.isFocused)
                                    viewModel.onAction(TaskCreationActions.SetTitleError(false))
                            },
                        colors =
                            TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFF2E4F57),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            )
                    )
                    TextField(
                        state = uiState.descriptionFieldState,
                        placeholder = { Text("Description") },
                        trailingIcon = {
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Clear description",
                                modifier =
                                    Modifier.size(14.dp).clickable {
                                        uiState.descriptionFieldState.clearText()
                                    }
                            )
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                        colors =
                            TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFF2E4F57),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                    )
                    DueDateAndNotifications(
                        onNotificationsToggle = { notificationsOn: Boolean ->
                            viewModel.onAction(
                                TaskCreationActions.OnNotificationsChanged(notificationsOn)
                            )
                        },
                        onDoesTaskRecurToggle = { doesTaskRecur ->
                            viewModel.onAction(TaskCreationActions.OnRecursChanged(doesTaskRecur))
                        },
                        notifySwitchIsChecked = uiState.notificationsOn,
                        recurringSwitchIsChecked = uiState.isRecurring,
                        dateFieldState = uiState.dateFieldState,
                        dateError = uiState.dateError,

                        onClearDateError = {
                            viewModel.onAction(TaskCreationActions.ClearDateError)
                        },
                        isDateFieldEnabled = isDateFieldEnabled,
                        onConfirmTime = {newTime -> viewModel.onAction(TaskCreationActions.OnTimeChange(newTime))},
                        timePickerState = uiState.timePickerState,
                    )
                    SpoonSelectionCard(
                        uiState.spoons,
                        { spoons ->
                            viewModel.onAction(TaskCreationActions.OnSpoonSelectedChanged(spoons))
                        }
                    )
                    CategoryAndPriorityCard(
                        onCategoryChanged = { category ->
                            viewModel.onAction(TaskCreationActions.OnCategoryChanged(category))
                        },
                        selectedCategory = uiState.category,
                        priority = uiState.priority,
                        onPriorityChanged = { priority ->
                            viewModel.onAction(TaskCreationActions.OnPriorityChanged(priority))
                        }
                    )
                }
            }
        }
    }
}
/*
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
private fun PreviewTaskCreationScreenGen() { // function that houses all UI on this screen.
    val titleState = rememberTextFieldState()
    val descriptionState = rememberTextFieldState()
    TaskCreationScreenGen(
        viewModel = TODO()
    )

}

 */

@Composable
fun DueDateAndNotifications(
    onNotificationsToggle: (Boolean) -> Unit,
    onDoesTaskRecurToggle: (Boolean) -> Unit,
    onClearDateError: () -> Unit = {},
    onConfirmTime: (TimePickerState) -> Unit,
    notifySwitchIsChecked: Boolean,
    recurringSwitchIsChecked: Boolean,
    timePickerState: TimePickerState,
    dateFieldState: TextFieldState,
    modifier: Modifier = Modifier,

    dateError: Boolean = false,

    isDateFieldEnabled: Boolean = true,
) {
    Card(modifier = Modifier) {
        Column(modifier = modifier.padding(10.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Notify Me")
                    Switch(
                        checked = notifySwitchIsChecked,
                        onCheckedChange = { onNotificationsToggle(it) },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                            )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Recurring")
                    Switch(
                        checked = recurringSwitchIsChecked,
                        onCheckedChange = {
                            onDoesTaskRecurToggle(it)
                        }, // use 2: change state of recurring task
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                            )
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // time field, digits only, max 4 chars: 10:30. No AM/PM
                Wrapper(
                    onConfirm = {timePickerState -> onConfirmTime(timePickerState)},
                    modifier = Modifier.weight(1f)
                )
                // date field, chars only, 6 digits max
                OutlinedTextField(
                    state = dateFieldState,
                    label = { Text("mm/dd/yy") },
                    enabled = isDateFieldEnabled,
                    isError = dateError,
                    supportingText = { if (dateError) Text("Enter a valid date (e.g. 12/22/26)") },
                    trailingIcon = {
                        Icon(
                            painter =
                                painterResource(
                                    Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                ),
                            contentDescription = "Clear date",
                            modifier =
                                modifier.size(18.dp).clickable {
                                    dateFieldState.clearText()
                                    onClearDateError()
                                }
                        )
                    },
                    inputTransformation =
                        InputTransformation {
                            val digitsOnly = asCharSequence().filter { it.isDigit() }.toString()
                            replace(0, length, digitsOnly)
                            if (length > 6) delete(6, length)
                        },
                    outputTransformation =
                        OutputTransformation {
                            if (length > 2) insert(2, "/")
                            if (length > 5) insert(5, "/")
                        },
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                    modifier =
                        modifier.weight(1f).onFocusChanged { if (it.isFocused) onClearDateError() }
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewDueDateAndNotifications(
    modifier: Modifier = Modifier
) { // TODO rewrite to use the actual component
    var notifySwitchIsChecked by remember { mutableStateOf(true) }
    var recurringSwitchChecked by remember { mutableStateOf(true) }
    var timeFieldState = rememberTextFieldState("")
    var dateFieldState = rememberTextFieldState("")
    Card(modifier = Modifier) {
        /*
         * the elements within this card can be grouped into two groups: The switches and the
         * input fields.These groups are stacked in a column.
         */
        Column( // column to stack the two groups
            modifier = modifier.padding(10.dp)
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
                    // , with a little space between each
                    // other
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Notify Me")

                    Switch(
                        checked = notifySwitchIsChecked,
                        onCheckedChange = { // lambda AKA anonymous function
                            notifySwitchIsChecked = it
                        },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                                // uncheckedThumbColor = Color(0xFF27567D),
                                // uncheckedTrackColor = Color(0xFFD0D7DB),
                                // uncheckedBorderColor = Color(0xFF27567D),
                            )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Recurring")

                    Switch(
                        checked = recurringSwitchChecked,
                        onCheckedChange = { // lambda AKA anonymous function
                            recurringSwitchChecked = it
                        },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                            )
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
                    label = { Text("HH:MM") },
                    trailingIcon = {
                        Icon(
                            painter =
                                painterResource(
                                    Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                ),
                            contentDescription = "Cancel Icon",
                            modifier =
                                modifier
                                    .size(18.dp) // scale the icon up so it is easily clickable
                                    .clickable { // lambda AKA anonymous function
                                        timeFieldState.clearText()
                                    },
                        )
                    },
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                    placeholder = { Text("HH:MM") },
                    // input requirements, must be a digit and must be of correct length
                    inputTransformation =
                        InputTransformation {
                            val digitsOnly = asCharSequence().filter { it.isDigit() }.toString()
                            replace(0, length, digitsOnly)
                            if (length > 4) delete(4, length)
                        },
                    outputTransformation =
                        OutputTransformation { // lambda AKA anonymous function
                            if (length > 2) insert(2, ":")
                        },
                    modifier =
                        modifier
                            // weight() is used so each TextField attempts to occupy
                            // equal space
                            .weight(1f)
                )
                OutlinedTextField(
                    state = dateFieldState,
                    label = { Text("mm/dd/yy") },
                    trailingIcon = {
                        Icon(
                            painter =
                                painterResource(
                                    Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                ),
                            contentDescription = "Spoon icon",
                            modifier =
                                modifier.size(18.dp).clickable { // lambda AKA anonymous function
                                    dateFieldState.clearText()
                                }
                        )
                    },
                    placeholder = { Text("mm/dd/yy") },
                    // input requirements, must be a digit and must be of correct length
                    inputTransformation =
                        InputTransformation {
                            val digitsOnly = asCharSequence().filter { it.isDigit() }.toString()
                            replace(0, length, digitsOnly)
                            if (length > 6) delete(6, length)
                        },
                    outputTransformation =
                        OutputTransformation { // lambda AKA anonymous function
                            if (length > 2) insert(2, "/")
                            if (length > 5) insert(5, "/")
                        },
                    modifier =
                        modifier
                            // weight() is used so each TextField attempts to occupy
                            // equal space
                            .weight(1f),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                )
            }
            Row { // AM/PM button selection
                var selectedIndex by remember { mutableIntStateOf(0) }
                val options = listOf("AM", "PM")
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape =
                                SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex,
                            label = { Text(label) },
                            colors =
                                SegmentedButtonColors(
                                    activeContainerColor = MaterialTheme.colorScheme.primary,
                                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                                    activeBorderColor = MaterialTheme.colorScheme.outline,
                                    inactiveContainerColor = MaterialTheme.colorScheme.secondary,
                                    inactiveContentColor = MaterialTheme.colorScheme.onSecondary,
                                    inactiveBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledActiveContainerColor =
                                        MaterialTheme.colorScheme.surfaceDim,
                                    disabledActiveContentColor =
                                        MaterialTheme.colorScheme.surfaceDim,
                                    disabledActiveBorderColor =
                                        MaterialTheme.colorScheme.surfaceDim,
                                    disabledInactiveContainerColor =
                                        MaterialTheme.colorScheme.surfaceDim,
                                    disabledInactiveContentColor =
                                        MaterialTheme.colorScheme.surfaceDim,
                                    disabledInactiveBorderColor =
                                        MaterialTheme.colorScheme.surfaceDim,
                                ),
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SpoonSelectionCard(
    selectedSpoon: Int,
    onSpoonChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    /*
    Potential Issues
     - THe clickable surface is too small and cumbersome to use effectively. CInsider replacing with
      a Surface.
      - The spoon icons need to be replaced to match the Figma
     */

    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = modifier.padding(12.dp).fillMaxWidth()) {
            Text("Spoons Required")
            Row( // how to center this in the card:
                // order of operations matters with modifiers!!!
                modifier = modifier.fillMaxWidth(), // give the element the max available width
                horizontalArrangement = Arrangement.Center, // center it along the horizontal axis
                verticalAlignment = Alignment.CenterVertically // center it along the vertical axis
            ) {
                for (i in 1..max_spoons) { // a for loop creates icons programmatically instead
                    // of specifying 5 Icons
                    if (i <= selectedSpoon) {
                        Icon(
                            painter = painterResource(Res.drawable.spoon_filled),
                            contentDescription = "Unfilled Spoon Icon",
                            modifier = modifier.size(64.dp).clickable { onSpoonChange(i) }
                        )
                    } else {
                        Icon(
                            painter = painterResource(Res.drawable.spoon_unfilled),
                            contentDescription = "Filled Spoon Icon",
                            modifier = modifier.size(64.dp).clickable { onSpoonChange(i) }
                        )
                    }
                }
            }
        }
    }
}
/*
@Preview
@Composable
private fun PreviewSpoonSelectionCard(){
    SpoonSelectionCard(
        onAction = {selection -> viewModel.onAction(TaskCreationActions.OnSelectedSpoonsChanged(selection)) },
        selectedSpoon = TODO()
    )
}

 */
@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
fun CategoryAndPriorityCard(
    onCategoryChanged: (Category) -> Unit,
    selectedCategory: Category,
    priority: Priority,
    onPriorityChanged: (Priority) -> Unit
) {
    // change to reference category enum

    var expanded by remember { mutableStateOf(false) } // can stay local
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Category")
                ExposedDropdownMenuBox(
                    expanded = expanded, // don't extract
                    onExpandedChange = { expanded = it }, // don't extract
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField( // Category Selection Box
                        value =
                            selectedCategory
                                .name, // DONE extract selected option, change menuItems to
                        // reference enum
                        onValueChange = {}, // call viewModel.OnCategoryChanged here?
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors =
                            ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),
                        modifier =
                            Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Category.entries.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    onCategoryChanged(category)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Priority Level")
                PrioritySelectButton(priority = priority, onPriorityChanged = onPriorityChanged)
            }
        }
    }
}

/*
@Preview
@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
fun PreviewCategoryAndPriorityCard() {
var selectedOption by remember { mutableIntStateOf(Category.HYGIENE.ordinal) }
var selectedCategory by remember { mutableStateOf(Category.HYGIENE) }
CategoryAndPriorityCard(
onCategoryChanged = { category ->
onAction(TaskCreationActions.OnCategoryChanged(category))
},
selectedCategory = selectedCategory
)
}
*/

@Composable
fun PrioritySelectButton(priority: Priority, onPriorityChanged: (Priority) -> Unit) {
    SingleChoiceSegmentedButtonRow {
        Priority.entries.forEachIndexed { index, option ->
            SegmentedButton(
                shape =
                    SegmentedButtonDefaults.itemShape(index = index, count = Priority.entries.size),
                onClick = { onPriorityChanged(option) },
                selected = option == priority,
                label = { Text(option.name.lowercase()) },
                colors =
                    SegmentedButtonColors(
                        activeContainerColor = MaterialTheme.colorScheme.primary,
                        activeContentColor = MaterialTheme.colorScheme.onPrimary,
                        activeBorderColor = MaterialTheme.colorScheme.outline,
                        inactiveContainerColor = MaterialTheme.colorScheme.secondary,
                        inactiveContentColor = MaterialTheme.colorScheme.onSecondary,
                        inactiveBorderColor = MaterialTheme.colorScheme.outline,
                        disabledActiveContainerColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledActiveContentColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledActiveBorderColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledInactiveContainerColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledInactiveContentColor = MaterialTheme.colorScheme.surfaceDim,
                        disabledInactiveBorderColor = MaterialTheme.colorScheme.surfaceDim
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(onConfirm: () -> Unit, onDismiss: () -> Unit, content: @Composable () -> Unit){

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 6.dp, modifier = Modifier.width(
            IntrinsicSize.Min).height(IntrinsicSize.Min).background(shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally){
                content()
                Row(
                    modifier = Modifier.height(40.dp).fillMaxWidth()
                ){
                    TextButton(onClick = onDismiss){Text("Cancel")}
                    TextButton(onClick = {
                                    onConfirm()
                                    onDismiss()
                                    },
                    ){Text("Ok")}
                }
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Wrapper(onConfirm: (TimePickerState) -> Unit, modifier: Modifier = Modifier){
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute,
        is24Hour = false
    )
    var showDialog by remember {mutableStateOf(false)}
    Box(modifier){
        ElevatedButton(onClick = {showDialog = true}){
            Text("${timePickerState.hour}:${timePickerState.minute} ${if (timePickerState.isPm) "PM" else "AM"}", style = MaterialTheme.typography.bodyLarge)
        }
        if (showDialog) {
            TimePickerDialog(
                onConfirm = { onConfirm(timePickerState) },
                onDismiss = { showDialog = false },
                content = { TimePicker(state = timePickerState) }
            )

        }
    }
}
@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PreviewWrapper(){
    Card{
        Wrapper({})
    }


}


