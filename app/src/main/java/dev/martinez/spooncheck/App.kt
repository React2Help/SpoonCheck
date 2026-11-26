@file:Suppress(
    "LongMethod",
)

package dev.martinez.spooncheck

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.martinez.spooncheck.repositories.EventRepository
import dev.martinez.spooncheck.repositories.SettingsThemeRepository
import dev.martinez.spooncheck.repositories.TaskRepository
import dev.martinez.spooncheck.routes.DashboardRoute
import dev.martinez.spooncheck.routes.PatternsRoute
import dev.martinez.spooncheck.routes.SettingsRoute
import dev.martinez.spooncheck.routes.TaskCreationRoute
import dev.martinez.spooncheck.routes.TaskListRoute
import dev.martinez.spooncheck.routes.ViewTaskRoute
import dev.martinez.spooncheck.theme.SpoonCheckTheme
import kotlinx.serialization.Serializable

@Suppress("LongMethod")
@Composable
fun App(taskRepository: TaskRepository, eventRepository: EventRepository) {

    val backstack = rememberNavBackStack( TaskList)

    // one shared theme repository for the whole app. Loads the saved theme from the device

    val themeRepository = remember { SettingsThemeRepository() }
    val savedTheme by themeRepository.theme.collectAsStateWithLifecycle()

    SpoonCheckTheme(appTheme = savedTheme) {
        NavDisplay(
            backStack = backstack,
            onBack = { backstack.removeLastOrNull() },
            entryDecorators = listOf(rememberViewModelStoreNavEntryDecorator()),
            entryProvider =
                entryProvider {
                    entry<Dashboard> {
                        DashboardRoute(
                            taskRepository = taskRepository,
                            onNavigateToTasks = { backstack.add(TaskList) },
                            onNavigateToPatterns = { backstack.add(Patterns) },
                            onNavigateToSettings = { backstack.add(Settings) },
                            onNavigateToTaskCreation = { backstack.add(CreateTaskDateEnabled) }
                        )
                    }

                    entry<TaskList> {
                        TaskListRoute(
                            taskRepository = taskRepository,
                            onNavigateToTaskCreationDateEnable = {
                                backstack.removeAll {
                                    it is CreateTaskDateEnabled || it is CreateTaskDateDisabled
                                }
                                backstack.add(CreateTaskDateEnabled)
                            },
                            onNavigateToPatterns = { backstack.add(Patterns) },
                            onNavigateToDashboard = { backstack.add(Dashboard) },
                            onNavigateToSettings = { backstack.add(Settings) },
                            onNavigateToTaskDetails = { taskId ->
                                backstack.add(ViewTask(taskId))
                            } // figure out how to feed the lambda through
                        )
                    }
                    entry<Patterns> {
                        PatternsRoute(
                            onNavigateToTaskCreationDateEnable = {
                                backstack.removeAll {
                                    it is CreateTaskDateEnabled || it is CreateTaskDateDisabled
                                }
                                backstack.add(CreateTaskDateEnabled)
                            },
                            onNavigateToPatterns = { backstack.add(Patterns) },
                            onNavigateToDashboard = { backstack.add(Dashboard) },
                            onNavigateToSettings = { backstack.add(Settings) },
                            onNavigateToTaskList = { backstack.add(TaskList) },
                            eventRepository = eventRepository,
                            taskRepository = taskRepository
                        )
                    }

                    entry<CreateTaskDateEnabled> {
                        TaskCreationRoute(
                            taskRepository = taskRepository,
                            isDateFieldEnabled = true,
                            onNavigateToTaskList = { backstack.add(TaskList) }
                        )
                    }
                    entry<Settings> {
                        SettingsRoute(
                            themeRepository = themeRepository,
                            onNavigateToTaskCreationDateEnable = {
                                backstack.removeAll {
                                    it is CreateTaskDateEnabled || it is CreateTaskDateDisabled
                                }
                                backstack.add(CreateTaskDateEnabled)
                            },
                            onNavigateToTaskCreationDateDisable = {
                                backstack.add(CreateTaskDateDisabled)
                            },
                            onNavigateBack = { backstack.removeLastOrNull() }
                        )
                    }
                    entry<ViewTask> { destination ->
                        ViewTaskRoute(
                            { backstack.removeLastOrNull() },
                            destination
                                .taskId, // TODO always zero? Or ViewModel's UI state is never
                            // updated?
                            taskRepository,
                            themeRepository
                        )
                    }
                },
        )
    }
}

@Serializable sealed interface Destination : NavKey

@Serializable data object Dashboard : Destination

@Serializable data object TaskList : Destination

@Serializable data object Patterns : Destination

@Serializable data class ViewTask(val taskId: Long) : Destination

@Serializable data object Account : Destination

@Serializable data object Settings : Destination

@Serializable data object CreateTaskDateEnabled : Destination

@Serializable data object CreateTaskDateDisabled : Destination
