package dev.react2help.spooncheck.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
    lightColorScheme(
        primary = DeepTeal,
        onPrimary = Color.White,
        primaryContainer = SoftTealContainer,
        onPrimaryContainer = DeepTealDark,
        secondary = MutedInk,
        onSecondary = Color.White,
        secondaryContainer = LavenderSurface,
        onSecondaryContainer = Ink,
        tertiary = CoralAccent,
        onTertiary = Ink,
        tertiaryContainer = Blush,
        onTertiaryContainer = Ink,
        background = SageBackground,
        onBackground = Ink,
        surface = CardWhite,
        onSurface = Ink,
        surfaceVariant = LavenderSurface,
        onSurfaceVariant = MutedInk,
        outline = OutlineGray,
        outlineVariant = DividerGray,
        inverseSurface = Ink,
        inverseOnSurface = CardWhite,
        inversePrimary = DarkPrimary,
        scrim = Color.Black
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = DarkPrimary,
        onPrimary = DarkOnPrimary,
        primaryContainer = DeepTealDark,
        onPrimaryContainer = SoftTealContainer,
        secondary = Color(0xFFC3CBC8),
        onSecondary = Color(0xFF2C3432),
        secondaryContainer = DarkSurfaceVariant,
        onSecondaryContainer = DarkOnSurface,
        tertiary = Color(0xFFFFB5A3),
        onTertiary = Color(0xFF5A1F12),
        tertiaryContainer = Color(0xFF713325),
        onTertiaryContainer = Color(0xFFFFDAD1),
        background = DarkBackground,
        onBackground = DarkOnSurface,
        surface = DarkSurface,
        onSurface = DarkOnSurface,
        surfaceVariant = DarkSurfaceVariant,
        onSurfaceVariant = Color(0xFFC3CBC8),
        outline = DarkOutline,
        outlineVariant = Color(0xFF414947)
    )

@Composable
fun PatternsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme =
        when {
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(colorScheme = colorScheme, typography = AppTypography, content = content)
}
