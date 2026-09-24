@file:Suppress("LongMethod", "MagicNumber")

package dev.react2help.spooncheck.ui

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import dev.react2help.spooncheck.theme.DashboardTextTeal
import dev.react2help.spooncheck.theme.DeepTeal
import dev.react2help.spooncheck.theme.SoftTealContainer
import dev.react2help.spooncheck.ui.components.DashboardCareRoutineCard
import dev.react2help.spooncheck.ui.components.DashboardImportantTasksCard
import dev.react2help.spooncheck.ui.components.DashboardSpoonGoalCard
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.dashboard_fab_icon
import spooncheck.shared.generated.resources.pine_tree_background

// ---------------------------------------------------------------------------
// Preview-only sample data
// ---------------------------------------------------------------------------

private val PreviewTasks =
    listOf(
        Task(
            id = 1L,
            title = "Pay Rent",
            description = "This is super important, do not forget!",
            spoons = 3,
            priority = Priority.critical,
            category = Category.NONE,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(10, 22, 0),
        ),
        Task(
            id = 2L,
            title = "Clean The Dishes",
            description = "Wash everything on the left side of the sink",
            spoons = 2,
            priority = Priority.high,
            category = Category.NONE,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = null,
        ),
    )

private data class DashboardBottomNavItem(
    val route: String,
    val label: String,
)

private val DashboardBottomNavItems =
    listOf(
        DashboardBottomNavItem(route = "dashboard", label = "Dashboard"),
        DashboardBottomNavItem(route = "tasks", label = "Tasks"),
        DashboardBottomNavItem(route = "patterns", label = "Patterns"),
    )

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    selectedRoute: String = "dashboard",
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.pine_tree_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3f,
            modifier = Modifier
                .fillMaxSize()
                .offset(y=-25.dp)
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { DashboardTopBar(userName = "Benjamin") },
            bottomBar = {
                DashboardBottomBar(selectedRoute = selectedRoute)
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = { DashboardFab() },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    DashboardSpoonGoalCard(
                        progress = 0.8f,
                        completed = 8,
                        total = 10,
                        onViewTasks = {},
                        modifier = Modifier.offset(y=8.dp)
                    )
                }
                item {
                    DashboardImportantTasksCard(
                        tasks = PreviewTasks,
                    )
                }
                item {
                    DashboardCareRoutineCard(
                        checkedInDays = 4,
                        restDays = 1,
                        totalDays = 7,
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Private layout helpers
// ---------------------------------------------------------------------------

@Composable
private fun DashboardTopBar(userName: String) {
    Surface(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)) {
        Row(
            modifier =
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
                    .size(72.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.size(40.dp)) // balances the avatar on the right
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
                    Modifier.size(40.dp)
                        .clip(CircleShape)
                        .background(SoftTealContainer),
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
    onDestinationSelected: (String) -> Unit = {},
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        modifier = Modifier.height(80.dp),
    ) {
        DashboardBottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == selectedRoute,
                onClick = { onDestinationSelected(item.route) },
                icon = {},
                label = { Text(item.label) },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = DeepTeal,
                        selectedTextColor = DeepTeal,
                        indicatorColor = DeepTeal.copy(alpha = 0.22f),
                    ),
            )
        }
    }
}

@Composable
private fun DashboardFab() {
    FloatingActionButton(
        onClick = {},
        modifier = Modifier
            .size(68.dp)
            .offset(y=36.dp),
        shape = CircleShape,
        containerColor = DeepTeal,
    ) {
        Image(
            painter = painterResource(Res.drawable.dashboard_fab_icon),
            contentDescription = "Check in",
            modifier = Modifier.size(64.dp),
        )
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = false, widthDp = 412, heightDp = 892)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen()
}
