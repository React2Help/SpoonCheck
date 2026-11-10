package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.react2help.spooncheck.repositories.EventRepository
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.routes.DashboardRoute
import dev.react2help.spooncheck.routes.PatternsRoute
import dev.react2help.spooncheck.routes.SettingsRoute
import dev.react2help.spooncheck.routes.TaskCreationRoute
import dev.react2help.spooncheck.routes.TaskListRoute
import dev.react2help.spooncheck.routes.ViewTaskRoute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Suppress("LongMethod")
@Composable
fun App(taskRepository: TaskRepository, eventRepository: EventRepository) {
    @OptIn(ExperimentalSerializationApi::class)
    val config = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) { subclassesOfSealed<Destination>() }
        }
    }
    val backstack = rememberNavBackStack(config, TaskList)

    MaterialTheme {
        NavDisplay(
            backStack = backstack,
            onBack = { backstack.removeLastOrNull() },
            entryProvider =
                entryProvider {
                    entry<Dashboard> { DashboardRoute() }

                    entry<TaskList> {
                        TaskListRoute(
                            taskRepository = taskRepository,
                            onNavigateToTaskCreationDateEnable = {
                                backstack.add(CreateTaskDateEnabled)
                            },
                            onNavigateToPatterns = { backstack.add(Patterns) },
                            onNavigateToDashboard = { backstack.add(Dashboard) },
                            onNavigateToSettings = { backstack.add(Settings) },
                            onNavigateToTaskDetails = { backstack.add(ViewTask) }
                        )
                    }
                    entry<Patterns> {
                        PatternsRoute(
                            onNavigateToTaskCreationDateEnable = {
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
                            onNavigateToTaskCreationDateEnable = {
                                backstack.add(CreateTaskDateEnabled)
                            },
                            onNavigateToTaskCreationDateDisable = {
                                backstack.add(CreateTaskDateDisabled)
                            },
                        )
                    }
                    entry<ViewTask> { ViewTaskRoute() }
                },
        )
    }
}

@Serializable sealed interface Destination : NavKey

@Serializable data object Dashboard : Destination

@Serializable data object TaskList : Destination

@Serializable data object Patterns : Destination

@Serializable data object ViewTask : Destination

@Serializable data object Account : Destination

@Serializable data object Settings : Destination

@Serializable data object CreateTaskDateEnabled : Destination

@Serializable data object CreateTaskDateDisabled : Destination
