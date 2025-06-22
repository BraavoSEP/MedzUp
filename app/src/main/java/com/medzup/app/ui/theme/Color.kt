package com.medzup.app.ui.theme

import androidx.compose.ui.graphics.Color

// 70s Vintage Palette
val VintageGreen = Color(0xFF3A6B35) // Deep olive/avocado green
val VintageYellow = Color(0xFFE3B448) // Harvest gold/mustard
val VintageOrange = Color(0xFFCBD18F) // Lighter, earthy tone
val VintageBrown = Color(0xFF5B2E48) // A contrasting deep color, like dark magenta/brown
val OffWhite = Color(0xFFF7F5E6) // Creamy off-white for backgrounds

val md_theme_light_primary = VintageGreen
val md_theme_light_onPrimary = OffWhite
val md_theme_light_primaryContainer = VintageOrange
val md_theme_light_onPrimaryContainer = Color(0xFF00210B)
val md_theme_light_secondary = VintageYellow
val md_theme_light_onSecondary = Color.Black
val md_theme_light_secondaryContainer = Color(0xFFDDE7C7)
val md_theme_light_onSecondaryContainer = Color(0xFF111F0F)
val md_theme_light_tertiary = VintageBrown
val md_theme_light_onTertiary = OffWhite
val md_theme_light_tertiaryContainer = Color(0xFFFFD9E2)
val md_theme_light_onTertiaryContainer = Color(0xFF3E001E)
val md_theme_light_error = Color(0xFFB3261E)
val md_theme_light_onError = Color.White
val md_theme_light_background = OffWhite
val md_theme_light_onBackground = Color(0xFF1A1C18)
val md_theme_light_surface = OffWhite
val md_theme_light_onSurface = Color(0xFF1A1C18)

// For dark theme, we can invert or use a different set of 70s-inspired colors
// For now, let's create a placeholder dark theme
val md_theme_dark_primary = VintageYellow
val md_theme_dark_onPrimary = Color(0xFF003918)
val md_theme_dark_primaryContainer = Color(0xFF005326)
val md_theme_dark_onPrimaryContainer = md_theme_light_primaryContainer
val md_theme_dark_secondary = VintageGreen
val md_theme_dark_onSecondary = Color(0xFF253422)
val md_theme_dark_secondaryContainer = Color(0xFF3B4B37)
val md_theme_dark_onSecondaryContainer = Color(0xFFDDE7C7)
val md_theme_dark_tertiary = md_theme_light_tertiary
val md_theme_dark_onTertiary = Color(0xFF5E1133)
val md_theme_dark_tertiaryContainer = Color(0xFF7B2949)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD9E2)
val md_theme_dark_error = Color(0xFFF2B8B5)
val md_theme_dark_onError = Color(0xFF601410)
val md_theme_dark_background = Color(0xFF1A1C18)
val md_theme_dark_onBackground = Color(0xFFE2E3DC)
val md_theme_dark_surface = Color(0xFF1A1C18)
val md_theme_dark_onSurface = Color(0xFFE2E3DC) 