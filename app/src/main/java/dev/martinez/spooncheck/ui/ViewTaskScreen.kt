@file:Suppress("MagicNumber")

package dev.martinez.spooncheck.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.martinez.spooncheck.DateFormatStyle
import dev.martinez.spooncheck.R
import dev.martinez.spooncheck.generateRandomFutureDate
import dev.martinez.spooncheck.modelsandstate.AppTheme
import dev.martinez.spooncheck.modelsandstate.Category
import dev.martinez.spooncheck.modelsandstate.Priority
import dev.martinez.spooncheck.modelsandstate.ViewTaskUIState
import dev.martinez.spooncheck.theme.SpoonCheckTheme
import dev.martinez.spooncheck.toLocalizedString
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ModifierMissing")
@Composable
fun ViewTaskScreen(onNavigateBack: () -> Unit, uiState: ViewTaskUIState) {

    when (uiState) {
        is ViewTaskUIState.Loading -> {
            Loading(onNavigateBack, uiState.theme)
        }
        is ViewTaskUIState.Content -> {
            RealContent(
                onNavigateBack = onNavigateBack,
                theme = uiState.theme,
                title = uiState.title,
                description = uiState.description,
                dueTime = uiState.dueTime.toLocalizedString(),
                dueDate = uiState.dueDate.toLocalizedString(style = DateFormatStyle.MEDIUM),
                category = uiState.category,
                priority = uiState.priority,
            )
        }
        is ViewTaskUIState.NotFound -> {
            NotFound(onNavigateBack, uiState.theme)
        }
        is ViewTaskUIState.Error -> {
            Error(onNavigateBack, uiState.theme, uiState.message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RealContent(
    onNavigateBack: () -> Unit,
    theme: AppTheme,
    title: String,
    description: String,
    dueTime: String,
    dueDate: String,
    category: Category,
    priority: Priority,
) {
    SpoonCheckTheme(theme) {
        BottomSheetScaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("View Task Details") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                painterResource(
                                    R.drawable.arrow_back_24dp,
                                ),
                                contentDescription = "Navigate Back",
                            )
                        }
                    },
                )
            },
            sheetContent = {},
        ) { paddingValues ->
            Column(
                modifier =
                    Modifier.fillMaxSize()
                        .padding(paddingValues)
                        .consumeWindowInsets(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                PriorityCategoryDueDate(priority, category, dueTime, dueDate)
                Text(description, style = MaterialTheme.typography.bodyMedium)
                Text(
                    "This task is set to recur on *placeholder* at *placeholder* until *placeholder",
                )
            }
        }
    }
}

@Composable
private fun PriorityCategoryDueDate(
    priority: Priority,
    category: Category,
    dueTime: String,
    dueDate: String,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        PillWithInformation {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.stat_1_24dp),
                    contentDescription = "Medium Priority Icon",
                )
                Text(priority.name, style = MaterialTheme.typography.labelLarge)
            }
        }
        PillWithInformation {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter =
                        painterResource(
                            R.drawable.category_24dp,
                        ),
                    contentDescription = "Category Icon",
                )
                Text(category.name, style = MaterialTheme.typography.labelLarge)
            }
        }
        PillWithInformation {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter =
                        painterResource(
                            R.drawable.calendar_clock_24dp,
                        ),
                    contentDescription = "Calendar and Clock Icon",
                )
                Column {
                    Text(dueDate, style = MaterialTheme.typography.labelLarge)
                    Text(dueTime, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
private fun PillWithInformation(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    content: @Composable () -> Unit,
) {
    Card(modifier = modifier, shape = RoundedCornerShape(20.dp)) {
        Box(modifier = Modifier.padding(contentPadding)) { content() }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun PriorityPill(priority: Priority) {
    Card(modifier = Modifier.size(128.dp)) {
        Box {
            MaterialShapes.Bun
            Text(priority.name)
        }
    }
}

@Preview
@Composable
private fun PreviewRealContent() {
    RealContent(
        onNavigateBack = {},
        theme = AppTheme.FOREST,
        title = "A Title",
        description = "A Description",
        dueTime = "10:26 AM",
        dueDate = "12/26/26",
        category = Category.Hygiene,
        priority = Priority.Medium,
    )
}

@Preview
@Composable
private fun PreviewPriorityPill() {
    PriorityPill(Priority.Medium)
}

@Composable
private fun Error(onNavigateBack: () -> Unit, theme: AppTheme, message: String) {
    SpoonCheckTheme(theme) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    title = { Text("View a Task") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                painter =
                                    painterResource(
                                        R.drawable.arrow_back_24dp,
                                    ),
                                contentDescription = "Navigate Backwards",
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier =
                    Modifier.padding(paddingValues)
                        .fillMaxSize()
                        .background(
                            Color(0xFFf6f7f7),
                        ), // background color of the resource not found graphic
            ) {
                Image(
                    painterResource(R.drawable.resource_not_found),
                    contentDescription =
                        "Image of a box with confetti around it. The words \"not found\" are underneath the box",
                    modifier = Modifier.size(256.dp),
                )
                Text("Whoops! ${message}.", style = MaterialTheme.typography.labelLarge)
                Button(onClick = { onNavigateBack() }) { Text("Go Back") }
            }
        }
    }
}

@Composable
private fun NotFound(onNavigateBack: () -> Unit, theme: AppTheme) {
    SpoonCheckTheme(theme) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    title = { Text("View a Task") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                painter =
                                    painterResource(
                                        R.drawable.arrow_back_24dp,
                                    ),
                                contentDescription = "Navigate Backwards",
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier =
                    Modifier.padding(paddingValues)
                        .fillMaxSize()
                        .background(
                            Color(0xFFf6f7f7),
                        ), // background color of the resource not found graphic
            ) {
                Image(
                    painterResource(R.drawable.resource_not_found),
                    contentDescription =
                        "Image of a box with confetti around it. The words \"not found\" are underneath the box",
                    modifier = Modifier.size(256.dp),
                )
            }
        }
    }
}

@Composable
private fun Loading(onNavigateBack: () -> Unit, theme: AppTheme) {
    SpoonCheckTheme(theme) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    title = { Text("View a Task") },
                    navigationIcon = {
                        IconButton(onClick = { onNavigateBack() }) {
                            Icon(
                                painter =
                                    painterResource(
                                        R.drawable.arrow_back_24dp,
                                    ),
                                contentDescription = "Navigate Backwards",
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(paddingValues).size(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewLoadingScreen() {
    ViewTaskScreen({}, ViewTaskUIState.Loading(AppTheme.FOREST))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewContentScreen() {
    ViewTaskScreen(
        {},
        ViewTaskUIState.Content(
            isLoading = false,
            title = "Pay the phone bill",
            description = "blah blah blah",
            priority = Priority.Medium,
            category = Category.Hygiene,
            dueDate = generateRandomFutureDate(),
            dueTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            isRecurring = true,
            notificationsOn = true,
            theme = AppTheme.FOREST,
        ),
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewNotFoundScreen() {
    ViewTaskScreen({}, ViewTaskUIState.NotFound(AppTheme.FOREST))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewErrorScreen() {
    ViewTaskScreen({}, ViewTaskUIState.Error("That task doesn't exist", AppTheme.FOREST))
}
