package dev.react2help.spooncheck.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.react2help.spooncheck.modelsandstate.DashboardActions
import dev.react2help.spooncheck.modelsandstate.DashboardUIState
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository
import dev.react2help.spooncheck.repositories.SampleTasks
import dev.react2help.spooncheck.theme.DashboardTextTeal
import dev.react2help.spooncheck.theme.DeepTeal
import dev.react2help.spooncheck.theme.SoftTealContainer
import dev.react2help.spooncheck.ui.components.DashboardCareRoutineCard
import dev.react2help.spooncheck.ui.components.DashboardImportantTasksCard
import dev.react2help.spooncheck.ui.components.DashboardSpoonGoalCard
import dev.react2help.spooncheck.viewmodels.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.dashboard_fab_icon
import spooncheck.shared.generated.resources.pine_tree_background

private const val DashboardRoute = "dashboard"
private const val TasksRoute = "tasks"
private const val PatternsRoute = "patterns"

private const val CareRoutineWeekLength = 7

private const val BackgroundImageAlpha = 0.3f
private val BackgroundImageOffsetY = (-25).dp

private val ContentSpacing = 8.dp
private val SpoonGoalCardOffsetY = 8.dp

private val TopBarHorizontalPadding = 16.dp
private val TopBarVerticalPadding = 12.dp
private val TopBarHeight = 72.dp
private val AvatarSize = 40.dp
private const val TopBarScrimAlpha = 0.8f

private const val BottomBarScrimAlpha = 0.95f
private val BottomBarHeight = 80.dp
private const val SelectedNavIndicatorAlpha = 0.22f

private val FabSize = 68.dp
private val FabOffsetY = 36.dp
private val FabIconSize = 64.dp

private const val PreviewWidthDp = 412
private const val PreviewHeightDp = 892

private data class DashboardBottomNavItem(
    val route: String,
    val label: String,
)

private val DashboardBottomNavItems =
    listOf(
        DashboardBottomNavItem(route = DashboardRoute, label = "Dashboard"),
        DashboardBottomNavItem(route = TasksRoute, label = "Tasks"),
        DashboardBottomNavItem(route = PatternsRoute, label = "Patterns"),
    )

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun DashboardScreen(
    state: DashboardUIState,
    onAction: (DashboardActions) -> Unit,
    modifier: Modifier = Modifier,
    selectedRoute: String = DashboardRoute,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val animatedProgress by animateFloatAsState(targetValue = state.spoonProgress)

    Box(modifier = modifier.fillMaxSize()) {
        DashboardBackground()
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = { DashboardTopBar(userName = state.userName) },
            bottomBar = {
                DashboardBottomBar(
                    selectedRoute = selectedRoute,
                    onDestinationSelect = { route ->
                        onAction(DashboardActions.OnDestinationSelect(route))
                    },
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                DashboardFab(onClick = { onAction(DashboardActions.OnFabClick) })
            },
        ) { paddingValues ->
            DashboardContent(
                state = state,
                progress = animatedProgress,
                contentPadding = paddingValues,
                onViewTasks = { onAction(DashboardActions.OnViewTasks) },
                onImportantTaskComplete = { task ->
                    showTaskCompletedSnackbar(
                        task = task,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        onAction = onAction,
                    )
                },
                onTaskClick = { task -> onAction(DashboardActions.OnTaskClick(task.id)) },
            )
        }
    }
}

@Composable
private fun DashboardBackground() {
    Image(
        painter = painterResource(Res.drawable.pine_tree_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = BackgroundImageAlpha,
        modifier = Modifier.fillMaxSize().offset(y = BackgroundImageOffsetY),
    )
}

@Composable
private fun DashboardContent(
    state: DashboardUIState,
    progress: Float,
    contentPadding: PaddingValues,
    onViewTasks: () -> Unit,
    onImportantTaskComplete: (Task) -> Unit,
    onTaskClick: (Task) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(contentPadding),
        contentPadding = PaddingValues(ContentSpacing),
        verticalArrangement = Arrangement.spacedBy(ContentSpacing),
    ) {
        item {
            DashboardSpoonGoalCard(
                progress = progress,
                completed = state.consumedSpoons,
                total = state.totalSpoons,
                onViewTasks = onViewTasks,
                modifier = Modifier.offset(y = SpoonGoalCardOffsetY),
            )
        }
        item {
            DashboardImportantTasksCard(
                tasks = state.importantTasks,
                onTaskComplete = onImportantTaskComplete,
                onTaskClick = onTaskClick,
            )
        }
        item {
            DashboardCareRoutineCard(
                checkedInDays = state.numCheckins,
                restDays = state.numRestDays,
                totalDays = CareRoutineWeekLength,
            )
        }
    }
}

private fun showTaskCompletedSnackbar(
    task: Task,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onAction: (DashboardActions) -> Unit,
) {
    onAction(DashboardActions.OnTaskCompletionChanged(task.id, isDone = true))
    scope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        val result =
            snackbarHostState.showSnackbar(
                message = "${task.title} completed",
                actionLabel = "Undo",
                duration = SnackbarDuration.Short,
            )
        if (result == SnackbarResult.ActionPerformed) {
            onAction(DashboardActions.OnTaskCompletionChanged(task.id, isDone = false))
        }
    }
}

// ---------------------------------------------------------------------------
// Private layout helpers
// ---------------------------------------------------------------------------

@Composable
private fun DashboardTopBar(userName: String) {
    Surface(color = MaterialTheme.colorScheme.surface.copy(alpha = TopBarScrimAlpha)) {
        Row(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(
                        horizontal = TopBarHorizontalPadding,
                        vertical = TopBarVerticalPadding,
                    )
                    .size(TopBarHeight),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.size(AvatarSize)) // balances the avatar on the right
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "Hey there, $userName!",
                    style = MaterialTheme.typography.titleMedium,
                    color = DashboardTextTeal,
                )
            }
            // Letter avatar — matches the Figma "Generic Avatar / Letter A" component
            Box(
                modifier =
                    Modifier.size(AvatarSize).clip(CircleShape).background(SoftTealContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "A",
                    style = MaterialTheme.typography.titleMedium,
                    color = DeepTeal,
                )
            }
        }
    }
}

@Composable
private fun DashboardBottomBar(
    selectedRoute: String,
    onDestinationSelect: (String) -> Unit = {},
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = BottomBarScrimAlpha),
        modifier = Modifier.height(BottomBarHeight),
    ) {
        DashboardBottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == selectedRoute,
                onClick = { onDestinationSelect(item.route) },
                icon = {},
                label = { Text(item.label) },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = DeepTeal,
                        selectedTextColor = DeepTeal,
                        indicatorColor = DeepTeal.copy(alpha = SelectedNavIndicatorAlpha),
                    ),
            )
        }
    }
}

@Composable
private fun DashboardFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(FabSize).offset(y = FabOffsetY),
        shape = CircleShape,
        containerColor = DeepTeal,
    ) {
        Image(
            painter = painterResource(Res.drawable.dashboard_fab_icon),
            contentDescription = "Check in",
            modifier = Modifier.size(FabIconSize),
        )
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = false, widthDp = PreviewWidthDp, heightDp = PreviewHeightDp)
@Composable
private fun DashboardScreenPreview() {
    // backed by a real ViewModel so swiping and undo work in Interactive Mode
    val viewModel = remember { DashboardViewModel(InMemoryTaskRepository(SampleTasks)) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(state = state, onAction = viewModel::onAction)
}
