package com.luisbarqueira.materialyoucompose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme as Material3MaterialTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppDarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    // shadow = md_theme_dark_shadow,
)

private val AppLightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    // shadow = md_theme_light_shadow,
)

// https://gist.github.com/hvisser/8db0669439bad5b8d7491d4ef3f6d3de
// conversions based on https://material.io/blog/migrating-material-3, deprecated colors set to Colors.Red
@Composable
fun fromMaterial3Theme(isLight: Boolean): Colors {
    val scheme = Material3MaterialTheme.colorScheme
    return Colors(
        primary = scheme.primary,
        onPrimary = scheme.onPrimary,
        primaryVariant = scheme.tertiary, // primaryVariant = Color.Red
        secondary = scheme.secondary,
        secondaryVariant = scheme.secondary, // secondaryVariant = Color.Red
        onSecondary = scheme.onSecondary,
        background = scheme.background,
        onBackground = scheme.onBackground,
        surface = scheme.surface,
        onSurface = scheme.onSurface,
        error = scheme.error,
        onError = scheme.onError,
        isLight = isLight
    )
}

@Composable
fun fromMaterial3Theme(): Typography {
    val typography = Material3MaterialTheme.typography
    return Typography(
        h1 = typography.displaySmall,
        h2 = typography.headlineLarge,
        h3 = typography.headlineMedium,
        h4 = typography.headlineSmall,
        h5 = typography.titleLarge,
        subtitle1 = typography.titleMedium,
        subtitle2 = typography.titleSmall,
        body1 = typography.bodyLarge,
        body2 = typography.bodyMedium,
        caption = typography.bodySmall,
        button = typography.labelLarge,
        overline = typography.labelMedium
    )
}

@Composable
fun MaterialBridgeTheme(
    colorScheme: ColorScheme,
    typography: androidx.compose.material3.Typography,
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    Material3MaterialTheme(colorScheme = colorScheme, typography = typography) {
        MaterialTheme(
            colors = fromMaterial3Theme(isLight = !isDark),
            typography = fromMaterial3Theme(),
            shapes = Shapes,
            content = content
        )
    }
}



@Composable
fun MaterialYouComposeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val myColorScheme = when {
        dynamicColor && isDarkTheme -> {
            dynamicDarkColorScheme(LocalContext.current)
        }
        dynamicColor && !isDarkTheme -> {
            dynamicLightColorScheme(LocalContext.current)
        }
        isDarkTheme -> AppDarkColorScheme
        else -> AppLightColorScheme
    }

    MaterialBridgeTheme(
        colorScheme = myColorScheme,
        typography = AppTypography,
        // Updates to Shapes coming soon
        // shapes = Shapes,
        isDark = isDarkTheme,
        content = content
    )
}