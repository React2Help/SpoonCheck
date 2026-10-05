package dev.react2help.spooncheck.theme

import androidx.compose.ui.graphics.Color

// Image-derived palette. Fine-tune when design tokens are finalized.

val SageBackground = Color(0xFFD3D8D3)
val CardWhite = Color(0xFFFFFFFF)
val DeepTeal = Color(0xFF2D5950)
val DeepTealDark = Color(0xFF1F403A)
val SoftTealContainer = Color(0xFFD7E7E2)

val Blush = Color(0xFFDAC3BF)
val CoralAccent = Color(0xFFE79A83)
val LavenderSurface = Color(0xFFF1EEF2)

val Ink = Color(0xFF1E1E1E)
val MutedInk = Color(0xFF5D6665)
val OutlineGray = Color(0xFF87918F)
val DividerGray = Color(0xFFD8D8D8)

// Dark-theme approximations until dedicated dark-mode tokens exist.
val DarkBackground = Color(0xFF111614)
val DarkSurface = Color(0xFF191F1D)
val DarkSurfaceVariant = Color(0xFF252A29)
val DarkPrimary = Color(0xFFA9D0C5)
val DarkOnPrimary = Color(0xFF12372F)
val DarkOnSurface = Color(0xFFE1E5E2)
val DarkOutline = Color(0xFF8E9996)

// ---- App themes selectable in Settings (see SpoonCheckTheme.kt) ----

// Forest
val ForestPrimary = Color(0xFF254A50)
val ForestSecondary = Color(0xFF7799A4)
val ForestThird = Color(0xFFD4E2E3)

// Beach
val BeachPrimary = Color(0xFF27567D)
val BeachSecondary = Color(0xFF5D82A2)
val BeachThird = Color(0xFFD0D7DB)

// Dark (suggested starting point: a deep blue-green night version of Forest)
val DarkThemePrimary = Color(0xFFA8CBD1) // light teal: headings, button text, selected toggle
val DarkThemeOnPrimary = Color(0xFF0F2A30) // dark text on top of DarkThemePrimary
val DarkThemeSecondary = Color(0xFF4F6E78) // muted teal: check button, unselected toggle
val DarkThemeThird = Color(0xFF2A3A3E) // button backgrounds
val DarkThemeBackground = Color(0xFF121A1C) // solid screen background (instead of a photo)
val DarkThemeSurface = Color(0xFF1A2427) // top/bottom bars
val DarkThemeCard = Color(0xFF243236) // cards
val DarkThemeOnSurface = Color(0xFFE1E8E9) // regular body text

// "Fourth": a very light tint used behind list items (task cards)
val ForestFourth = Color(0xFFFFFCF6)
val BeachFourth = Color(0xFFF6FEFF)
val DarkThemeFourth = Color(0xFF2C3D41) // a step lighter than DarkThemeSurface so items stand out
