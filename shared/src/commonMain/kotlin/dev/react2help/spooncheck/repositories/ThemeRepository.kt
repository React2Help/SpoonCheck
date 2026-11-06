package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.modelsandstate.AppTheme
import kotlinx.coroutines.flow.StateFlow

interface ThemeRepository {
    // last saved theme from user, starts as forest on first launch
    val theme: StateFlow<AppTheme>

    fun saveTheme(theme: AppTheme)
}
