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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.react2help.spooncheck.modelsandstate.AppTheme
import dev.react2help.spooncheck.modelsandstate.SettingsActions
import dev.react2help.spooncheck.modelsandstate.SettingsUIState
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.pine_tree_background
import spooncheck.shared.generated.resources.qr_code_scanner

// main screen dev version
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun SettingsScreen() {
    MaterialTheme {
        Scaffold(
            topBar = { // define the Header
                TopAppBar(
                    title = { Text("Settings", fontWeight = FontWeight.Bold) },
                    subtitle = { Text("") },
                    titleHorizontalAlignment = Alignment.CenterHorizontally
                )
            },
            bottomBar = { // define the two buttons on the bottom of the screen
                BottomAppBar(
                    actions = {
                        IconButton(onClick = {}) { // todo add a callback function here
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Cancel Icon",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    floatingActionButton = { // RHS button with the special styling
                        FloatingActionButton(
                            onClick = {}, // add a callback function here
                            containerColor = Color(0xFF7799A4),
                            contentColor = Color(0xFFFFFFFF),
                        ) {
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Check Button",
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
                        Arrangement.spacedBy(12.dp), // control how the elements are
                    horizontalAlignment = Alignment.CenterHorizontally,
                    // placed on the Vertical axis (now set for scrolling).
                    modifier =
                        Modifier.fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        onClick = { /* Handle click */},
                        content = {
                            Image(
                                painter = painterResource(Res.drawable.qr_code_scanner),
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(30.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Link Account", color = Color(0xFF254A50), fontSize = 20.sp)
                        },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                containerColor = Color.White
                            ),
                        modifier = Modifier.width(255.dp).height(85.dp),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.size(0.dp))
                    GeneralCard()
                    ThemeCard()
                    AccountSettingsCard()
                }
            }
        }
    }
}

// general settings card, notifications and replay tutorial
@Preview
@Composable
fun GeneralCard(modifier: Modifier = Modifier) {
    var notificationsSwitchIsChecked by remember { mutableStateOf(true) }
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("General", color = Color(0xFF254A50), fontWeight = FontWeight.Bold)
                // Spacer(modifier = Modifier.size(10.dp))

                // notifications preferences section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Enable Notifications")
                    Switch(
                        checked = notificationsSwitchIsChecked,
                        onCheckedChange = { notificationsSwitchIsChecked = it },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFFFFFFF),
                                checkedTrackColor = Color(0xFF2E4F57),
                            )
                    )
                }

                // replay tutorial button
                Button(
                    onClick = { /* Handle click */},
                    content = { Text("Replay Tutorial") },
                    colors =
                        ButtonDefaults.buttonColors(
                            contentColor = Color(0xFF254A50),
                            containerColor = Color(0xFFD4E2E3)
                        ),
                )
            }
        }
    }
}

// the theme card, text and buttons
@Preview
@Composable
fun ThemeCard(modifier: Modifier = Modifier) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Theme", color = Color(0xFF254A50), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(10.dp))
                ThemeSelectButton()
            }
        }
    }
}

// button definition for the theme card
@Composable
fun ThemeSelectButton(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Forest", "Beach", "HighContrast")
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = {
                    selectedIndex = index
                }, // this callback function should change the value
                // in the view model instead, but for now I'm leaving that until it's time to
                // refactor
                selected = index == selectedIndex,
                label = { Text(label) },
                colors =
                    SegmentedButtonColors(
                        activeContainerColor = Color(0xFF2E4F57),
                        activeContentColor = Color(0xFFFFFFFF),
                        activeBorderColor = MaterialTheme.colorScheme.outline,
                        inactiveContainerColor = Color(0xFF7799A4),
                        inactiveContentColor = Color(0xFFFFFFFF),
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

// account settings card, change email, password and log out button
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun AccountSettingsCard(modifier: Modifier = Modifier) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Account Settings", color = Color(0xFF254A50), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(10.dp))
                // name section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* Handle click */},
                        content = { Text("Change Userame") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF254A50),
                                containerColor = Color(0xFFD4E2E3)
                            ),
                    )
                    Text("John Smith", fontWeight = FontWeight.Bold)
                }
                // email section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Button(
                        onClick = { /* Handle click */},
                        content = { Text("   Change Email   ") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF254A50),
                                containerColor = Color(0xFFD4E2E3)
                            ),
                    )
                    Text("johnJsmith27.@gmail.com", fontWeight = FontWeight.Bold)
                }

                // password reset section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* Handle click */},
                        content = { Text(" Password Reset ") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF254A50),
                                containerColor = Color(0xFFD4E2E3)
                            ),
                    )
                    Text("*********", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.size(20.dp))

                // log out button
                Button(
                    onClick = { /* Handle click */},
                    content = { Text("         Log Out         ") },
                    colors =
                        ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFF57878)
                        ),
                )
            }
        }
    }
}

// appears when the user has unsaved changes
@Preview
@Composable
fun UnsavedChangesCard(modifier: Modifier = Modifier) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Careful, you have unsaved changes",
                    color = Color(0xFFF57878),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// main screen production version
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreenGen(onAction: (SettingsActions) -> Unit, state: SettingsUIState) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings", fontWeight = FontWeight.Bold) },
                    subtitle = { Text("") },
                    titleHorizontalAlignment = Alignment.CenterHorizontally
                )
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { onAction(SettingsActions.OnCancel) }) {
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.cancel_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Discard changes",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { onAction(SettingsActions.OnSave) },
                            containerColor = Color(0xFF7799A4),
                            contentColor = Color(0xFFFFFFFF),
                        ) {
                            Icon(
                                painter =
                                    painterResource(
                                        Res.drawable.check_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
                                    ),
                                contentDescription = "Save settings",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box {
                Image(
                    painter = painterResource(Res.drawable.pine_tree_background),
                    contentDescription = "Background Image of a grove of pine trees.",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxHeight()
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier =
                        Modifier.fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    if (state.hasUnsavedChanges) {
                        UnsavedChangesCard()
                    }
                    Button(
                        onClick = { /* Handle click */},
                        content = {
                            Image(
                                painter = painterResource(Res.drawable.qr_code_scanner),
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(30.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Link Account", color = Color(0xFF254A50), fontSize = 20.sp)
                        },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                containerColor = Color.White
                            ),
                        modifier = Modifier.width(255.dp).height(85.dp),
                        shape = RoundedCornerShape(15.dp)
                    )
                    GeneralCard(
                        onAction = onAction,
                        notificationsEnabled = state.notificationsEnabled
                    )
                    ThemeCard(onAction = onAction, selectedTheme = state.selectedTheme)
                    AccountSettingsCard(
                        onAction = onAction,
                        userName = state.userName,
                        userEmail = state.userEmail,
                        password = state.password
                    )
                }
            }
        }
    }
}

@Composable
fun GeneralCard(
    onAction: (SettingsActions) -> Unit,
    notificationsEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("General", color = Color(0xFF254A50), fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Enable Notifications")
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { onAction(SettingsActions.OnNotificationsChanged(it)) },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFFFFFFF),
                                checkedTrackColor = Color(0xFF2E4F57),
                            )
                    )
                }
                Button(
                    onClick = { onAction(SettingsActions.OnPasswordReset) },
                    content = { Text("Replay Tutorial") },
                    colors =
                        ButtonDefaults.buttonColors(
                            contentColor = Color(0xFF254A50),
                            containerColor = Color(0xFFD4E2E3)
                        ),
                )
            }
        }
    }
}

@Composable
fun ThemeCard(
    onAction: (SettingsActions) -> Unit,
    selectedTheme: AppTheme,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Theme", color = Color(0xFF254A50), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(10.dp))
                val options =
                    listOf(
                        AppTheme.FOREST to "Forest",
                        AppTheme.BEACH to "Beach",
                        AppTheme.HIGH_CONTRAST to "HighContrast"
                    )
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, (theme, label) ->
                        SegmentedButton(
                            shape =
                                SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                            onClick = { onAction(SettingsActions.OnThemeChanged(theme)) },
                            selected = selectedTheme == theme,
                            label = { Text(label) },
                            colors =
                                SegmentedButtonColors(
                                    activeContainerColor = Color(0xFF2E4F57),
                                    activeContentColor = Color(0xFFFFFFFF),
                                    activeBorderColor = MaterialTheme.colorScheme.outline,
                                    inactiveContainerColor = Color(0xFF7799A4),
                                    inactiveContentColor = Color(0xFFFFFFFF),
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
                                )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AccountSettingsCard(
    onAction: (SettingsActions) -> Unit,
    userName: String,
    userEmail: String,
    password: String,
    modifier: Modifier = Modifier
) {
    var isEditingName by remember { mutableStateOf(false) }
    var isEditingEmail by remember { mutableStateOf(false) }
    var isEditingPassword by remember { mutableStateOf(false) }

    var nameDraft by remember(userName) { mutableStateOf(userName) }
    var emailDraft by remember(userEmail) { mutableStateOf(userEmail) }
    var passwordDraft by remember { mutableStateOf("") }

    Card(modifier = modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Account Settings", color = Color(0xFF254A50), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(10.dp))

                // Username row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { isEditingName = !isEditingName },
                        content = { Text("Change Username") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF254A50),
                                containerColor = Color(0xFFD4E2E3)
                            ),
                    )
                    if (isEditingName) {
                        OutlinedTextField(
                            value = nameDraft,
                            onValueChange = { nameDraft = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions =
                                KeyboardActions(
                                    onDone = {
                                        onAction(SettingsActions.OnNameChanged(nameDraft))
                                        isEditingName = false
                                    }
                                ),
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(userName.ifBlank { "—" }, fontWeight = FontWeight.Bold)
                    }
                }

                // Email row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Button(
                        onClick = { isEditingEmail = !isEditingEmail },
                        content = { Text("   Change Email   ") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF254A50),
                                containerColor = Color(0xFFD4E2E3)
                            ),
                    )
                    if (isEditingEmail) {
                        OutlinedTextField(
                            value = emailDraft,
                            onValueChange = { emailDraft = it },
                            singleLine = true,
                            keyboardOptions =
                                KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Done
                                ),
                            keyboardActions =
                                KeyboardActions(
                                    onDone = {
                                        onAction(SettingsActions.OnEmailChanged(emailDraft))
                                        isEditingEmail = false
                                    }
                                ),
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(userEmail.ifBlank { "—" }, fontWeight = FontWeight.Bold)
                    }
                }

                // Password row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { isEditingPassword = !isEditingPassword },
                        content = { Text(" Password Reset ") },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF254A50),
                                containerColor = Color(0xFFD4E2E3)
                            ),
                    )
                    if (isEditingPassword) {
                        OutlinedTextField(
                            value = passwordDraft,
                            onValueChange = { passwordDraft = it },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions =
                                KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                            keyboardActions =
                                KeyboardActions(
                                    onDone = {
                                        onAction(SettingsActions.OnPasswordChanged(passwordDraft))
                                        passwordDraft = ""
                                        isEditingPassword = false
                                    }
                                ),
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            if (password.isBlank()) "*********" else "*".repeat(password.length),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    onClick = { onAction(SettingsActions.OnLogOut) },
                    content = { Text("         Log Out         ") },
                    colors =
                        ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFF57878)
                        ),
                )
            }
        }
    }
}
