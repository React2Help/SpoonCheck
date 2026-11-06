package dev.react2help.spooncheck.repositories

// state flow: holds current value and tells watchers when it updates
import com.russhwolf.settings.Settings
import dev.react2help.spooncheck.modelsandstate.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// stores the theme on the device so it persists if the app restarts
class SettingsThemeRepository(private val settings: Settings = Settings()) : ThemeRepository {

    // private, editable only in this file
    private val _theme = MutableStateFlow(loadTheme())
    // public, read only
    override val theme: StateFlow<AppTheme> = _theme.asStateFlow()

    override fun saveTheme(theme: AppTheme) {
        settings.putString("app_theme", theme.name)
        _theme.value = theme
    }

    private fun loadTheme(): AppTheme {
        val stored = settings.getStringOrNull("app_theme")
        // stored value or forest (default)
        return AppTheme.entries.firstOrNull { it.name == stored } ?: AppTheme.FOREST
    }
}
