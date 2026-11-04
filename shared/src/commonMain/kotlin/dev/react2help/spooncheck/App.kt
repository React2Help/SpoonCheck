package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.routes.SettingsRoute
import dev.react2help.spooncheck.routes.TaskCreationRoute
import dev.react2help.spooncheck.routes.TaskListRoute
import dev.react2help.spooncheck.ui.PatternsScreen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Suppress("LongMethod")
@Composable
fun App(taskRepository: TaskRepository) {
    @OptIn(ExperimentalSerializationApi::class)
    val config = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) { subclassesOfSealed<Destination>() }
        }
    }
    val backstack = rememberNavBackStack(config, Dashboard)

    MaterialTheme {
        NavDisplay(
            backStack = backstack,
            onBack = { backstack.removeLastOrNull() },
            entryProvider =
                entryProvider {
                    entry<Dashboard> {}

                    entry<TaskList> {
                        TaskListRoute(
                            taskRepository = taskRepository,
                            onNavigateToTaskCreationDateEnable = {
                                backstack.add(CreateTaskDateEnabled)
                            },
                            onNavigateToPatterns = { backstack.add(Patterns) },
                            onNavigateToDashboard = { backstack.add(Dashboard) },
                            onNavigateToAccount = { backstack.add(Account) },
                            onNavigateToTaskDetails = { backstack.add(ViewTask) }
                        )
                    }
                    entry<Patterns> { PatternsScreen() }
                    entry<Account> {}

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
                    entry<ViewTask> {}
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
