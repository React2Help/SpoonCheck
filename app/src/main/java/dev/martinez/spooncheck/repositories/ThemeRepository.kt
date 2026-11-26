package dev.martinez.spooncheck.repositories

import dev.martinez.spooncheck.modelsandstate.AppTheme
import kotlinx.coroutines.flow.StateFlow

interface ThemeRepository {
    // last saved theme from user, starts as forest on first launch
    val theme: StateFlow<AppTheme>

    fun saveTheme(theme: AppTheme)
}
