package com.itami.calorie_tracker.core.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.itami.calorie_tracker.core.domain.model.Theme

private val lightTheme = CalorieTrackerColors(
    background = Color(0XFFFFFFFF),
    onBackground = Color(0XFF0D1220),
    onBackgroundVariant = Color(0XFF6E7179),
    primary = Color(0xFF35CC8C),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFAA9BD2),
    onSecondary = Color(0xFF242833),
    surfacePrimary = Color(0xFFF7F7F7),
    onSurfacePrimary = Color(0xFF0D1220),
    surfaceSecondary = Color(0xFFDBFBED),
    onSurfaceSecondary = Color(0xFF0D1220),
    surfaceVariant = Color(0xFFCCCCCC),
    onSurfaceVariant = Color(0xFF808080),
    outline = Color(0xFFD9D9D9),
    outlineVariant = Color(0xFFBFBFBF),
    bottomBarContainer = Color(0xFFDBFBED),
    navigationItem = Color(0xFF35CC8C),
    red = Color(0xFFE56571),
    orange = Color(0xFFFFA072),
    lightGreen = Color(0xFF59FFC4),
    green = Color(0xFF35CC8C),
    lighterBlue = Color(0xFF81DDFF),
    lightBlue = Color(0xFF78ABFF),
)

private val darkTheme = CalorieTrackerColors(
    background = Color(0XFF0D0D0D),
    onBackground = Color(0XFFB3B3B3),
    onBackgroundVariant = Color(0XFF6E7179),
    primary = Color(0xFF49D199),
    onPrimary = Color(0xFF242833),
    secondary = Color(0xFFAA9BD2),
    onSecondary = Color(0xFF242833),
    surfacePrimary = Color(0xFF242833),
    onSurfacePrimary = Color(0xFFE6E6E6),
    surfaceSecondary = Color(0xFF242833),
    onSurfaceSecondary = Color(0xFFE6E6E6),
    surfaceVariant = Color(0xFF3E414D),
    onSurfaceVariant = Color(0xFF808080),
    outline = Color(0xFF6E7179),
    outlineVariant = Color(0xFF242833),
    bottomBarContainer = Color(0xFF073622),
    navigationItem = Color(0xFF49D199),
    red = Color(0xFFB24F58),
    orange = Color(0xFFCB7F5B),
    lightGreen = Color(0xFF41FFBB),
    green = Color(0xFF2DAE77),
    lighterBlue = Color(0xFF78CEEE),
    lightBlue = Color(0xFF6D9CE8),
)

private val typography = CalorieTrackerTypography()
private val shapes = CalorieTrackerShapes()
private val spacing = CalorieTrackerSpacing()
private val padding = CalorieTrackerPadding()

private val LocalCalorieTrackerColorsProvider = staticCompositionLocalOf { lightTheme }
private val LocalCalorieTrackerTypographyProvider = staticCompositionLocalOf { typography }
private val LocalCalorieTrackerShapesProvider = staticCompositionLocalOf { shapes }
private val LocalCalorieTrackerSpacingProvider = staticCompositionLocalOf { spacing }
private val LocalCalorieTrackerPaddingProvider = staticCompositionLocalOf { padding }
private val LocalIsDarkThemeProvider = staticCompositionLocalOf { false }

object CalorieTrackerTheme {

    val colors: CalorieTrackerColors
        @Composable
        get() = LocalCalorieTrackerColorsProvider.current

    val typography: CalorieTrackerTypography
        @Composable
        get() = LocalCalorieTrackerTypographyProvider.current

    val shapes: CalorieTrackerShapes
        @Composable
        get() = LocalCalorieTrackerShapesProvider.current

    val spacing: CalorieTrackerSpacing
        @Composable
        get() = LocalCalorieTrackerSpacingProvider.current

    val padding: CalorieTrackerPadding
        @Composable
        get() = LocalCalorieTrackerPaddingProvider.current

    val isDarkTheme: Boolean
        @Composable
        get() = LocalIsDarkThemeProvider.current
}

@Composable
fun CalorieTrackerTheme(
    theme: Theme = Theme.SYSTEM_THEME,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = when (theme) {
        Theme.DARK_THEME -> true
        Theme.LIGHT_THEME -> false
        Theme.SYSTEM_THEME -> isSystemInDarkTheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalCalorieTrackerColorsProvider provides if (isDarkTheme) darkTheme else lightTheme,
        LocalCalorieTrackerTypographyProvider provides typography,
        LocalCalorieTrackerShapesProvider provides shapes,
        LocalCalorieTrackerSpacingProvider provides spacing,
        LocalCalorieTrackerPaddingProvider provides padding,
        LocalIsDarkThemeProvider provides isDarkTheme,
        content = content
    )
}