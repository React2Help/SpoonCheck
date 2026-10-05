package dev.react2help.spooncheck.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import dev.react2help.spooncheck.modelsandstate.AppTheme
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.ocean_view
import spooncheck.shared.generated.resources.pine_tree_background

/*
 * How the theme colors map onto MaterialTheme.colorScheme. Use these roles in screens instead of
 * hard-coded Color(0xFF...) values so every screen follows the user's chosen theme:
 *
 *   colorScheme.primary      -> "Primary": headings, selected toggle, switch track
 *   colorScheme.onPrimary    -> text/icons drawn on top of primary
 *   colorScheme.secondary    -> "Secondary": check (FAB) button, unselected toggle
 *   colorScheme.onSecondary  -> text/icons drawn on top of secondary
 *   colorScheme.tertiary     -> "Third": regular button backgrounds
 *   colorScheme.onTertiary   -> text on those buttons
 *   colorScheme.surface      -> top/bottom bars, white "Link Account"-style buttons
 *   colorScheme.fourth       -> "Fourth": light tint behind list items (defined below)
 *
 * The background image (or solid color for Dark) comes from ThemedBackground() below.
 */

private val ForestColorScheme =
    lightColorScheme(
        primary = ForestPrimary,
        onPrimary = Color.White,
        secondary = ForestSecondary,
        onSecondary = Color.White,
        tertiary = ForestThird,
        onTertiary = ForestPrimary,
        surface = Color.White,
    )

private val BeachColorScheme =
    lightColorScheme(
        primary = BeachPrimary,
        onPrimary = Color.White,
        secondary = BeachSecondary,
        onSecondary = Color.White,
        tertiary = BeachThird,
        onTertiary = BeachPrimary,
        surface = Color.White,
    )

private val NightColorScheme =
    darkColorScheme(
        primary = DarkThemePrimary,
        onPrimary = DarkThemeOnPrimary,
        secondary = DarkThemeSecondary,
        onSecondary = Color.White,
        tertiary = DarkThemeThird,
        onTertiary = DarkThemePrimary,
        background = DarkThemeBackground,
        onBackground = DarkThemeOnSurface,
        surface = DarkThemeSurface,
        onSurface = DarkThemeOnSurface,
        surfaceContainerHighest = DarkThemeCard, // default Card color
        surfaceVariant = DarkThemeCard,
        onSurfaceVariant = DarkThemeOnSurface,
    )

private fun AppTheme.colorScheme(): ColorScheme =
    when (this) {
        AppTheme.FOREST -> ForestColorScheme
        AppTheme.BEACH -> BeachColorScheme
        AppTheme.DARK -> NightColorScheme
    }

// current active theme
val LocalAppTheme = staticCompositionLocalOf { AppTheme.FOREST }

// wrap UI with this
@Composable
fun SpoonCheckTheme(appTheme: AppTheme, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppTheme provides appTheme) {
        MaterialTheme(colorScheme = appTheme.colorScheme(), content = content)
    }
}

// draws in background dependent on currently selected app theme
@Composable
fun ThemedBackground(modifier: Modifier = Modifier) {
    when (LocalAppTheme.current) {
        AppTheme.FOREST ->
            Image(
                painter = painterResource(Res.drawable.pine_tree_background),
                contentDescription = "Background Image of a grove of pine trees.",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        AppTheme.BEACH ->
            Image(
                painter = painterResource(Res.drawable.ocean_view),
                contentDescription = "Background Image of an ocean view.",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        AppTheme.DARK -> Box(modifier = modifier.fillMaxSize().background(DarkThemeBackground))
    }
}

// extension property because Material doesn't support a 4th color
val ColorScheme.fourth: Color
    @Composable
    @ReadOnlyComposable
    get() =
        when (LocalAppTheme.current) {
            AppTheme.FOREST -> ForestFourth
            AppTheme.BEACH -> BeachFourth
            AppTheme.DARK -> DarkThemeFourth
        }
