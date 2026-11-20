package dev.react2help.spooncheck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.react2help.spooncheck.modelsandstate.ViewTaskUIState
import dev.react2help.spooncheck.repositories.TaskRepository
import dev.react2help.spooncheck.repositories.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewTaskViewModel(
    val taskId: Long,
    val taskRepository: TaskRepository,
    val themeRepository: ThemeRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ViewTaskUIState>(ViewTaskUIState.Loading(themeRepository.theme.value))
    val uiState = _uiState.asStateFlow()

    init {
        loadTask()
    }

    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    fun loadTask() {
        viewModelScope.launch {
            println("ViewTaskViewModel:: Task ID: ${taskId}")
            try {
                val task = taskRepository.getTaskById(taskId)
                _uiState.value =
                    if (task == null) {
                        ViewTaskUIState.NotFound(themeRepository.theme.value)
                    } else {
                        ViewTaskUIState.Content(
                            isLoading = false,
                            title = task.title,
                            description = task.description,
                            priority = task.priority,
                            category = task.category,
                            dueDate = task.dueDate,
                            dueTime = task.dueTime,
                            isRecurring =
                                false, // todo migrate database to include these two fields
                            notificationsOn = false,
                            theme = themeRepository.theme.value
                        )
                    }
            } catch (e: Exception) {
                _uiState.value =
                    ViewTaskUIState.Error("Unable to load task", themeRepository.theme.value)
            }
        }
    }

    companion object {
        fun provideFactory(
            taskId: Long,
            taskRepository: TaskRepository,
            themeRepository: ThemeRepository
        ) = viewModelFactory {
            initializer { ViewTaskViewModel(taskId, taskRepository, themeRepository) }
        }
    }
}
