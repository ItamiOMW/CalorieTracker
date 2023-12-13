package com.itami.calorie_tracker.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.itami.calorie_tracker.core.domain.model.Theme

private val lightTheme = CalorieTrackerColors(
    background = Color(0XFFFFFFFF),
    onBackground = Color(0XFF0D1220),
    onBackgroundVariant = Color(0XFF6E7179),
    primary = Color(0xFF35CC8C),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFAA9BD2),
    onSecondary = Color(0xFF242833),
    surfacePrimary = Color(0xFFF2F2F2),
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
    onBackgroundVariant = Color(0XFF656565),
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
    outline = Color(0xFF666666),
    outlineVariant = Color(0xFF242833),
    bottomBarContainer = Color(0xFF0D0D0D),
    navigationItem = Color(0xFF90A6BA),
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

private val CalorieTrackerColorsProvider = staticCompositionLocalOf { lightTheme }
private val CalorieTrackerTypographyProvider = staticCompositionLocalOf { typography }
private val CalorieTrackerShapesProvider = staticCompositionLocalOf { shapes }
private val CalorieTrackerSpacingProvider = staticCompositionLocalOf { spacing }
private val CalorieTrackerPaddingProvider = staticCompositionLocalOf { padding }
private val IsDarkThemeProvider = staticCompositionLocalOf { false }

object CalorieTrackerTheme {

    val colors: CalorieTrackerColors
        @Composable
        get() = CalorieTrackerColorsProvider.current

    val typography: CalorieTrackerTypography
        @Composable
        get() = CalorieTrackerTypographyProvider.current

    val shapes: CalorieTrackerShapes
        @Composable
        get() = CalorieTrackerShapesProvider.current

    val spacing: CalorieTrackerSpacing
        @Composable
        get() = CalorieTrackerSpacingProvider.current

    val padding: CalorieTrackerPadding
        @Composable
        get() = CalorieTrackerPaddingProvider.current

    val isDarkTheme: Boolean
        @Composable
        get() = IsDarkThemeProvider.current
}

@Composable
fun CalorieTrackerTheme(
    theme: Theme = Theme.SYSTEM_THEME,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = when(theme) {
        Theme.DARK_THEME -> true
        Theme.LIGHT_THEME -> false
        Theme.SYSTEM_THEME -> isSystemInDarkTheme()
    }

    val systemUiController = rememberSystemUiController()
    DisposableEffect(key1 = systemUiController, key2 = isDarkTheme) {
        systemUiController.setSystemBarsColor(
            darkIcons = !isDarkTheme,
            color = if (isDarkTheme) darkTheme.background else lightTheme.background,
        )
        onDispose { }
    }

    CompositionLocalProvider(
        CalorieTrackerColorsProvider provides if (isDarkTheme) darkTheme else lightTheme,
        CalorieTrackerTypographyProvider provides typography,
        CalorieTrackerShapesProvider provides shapes,
        CalorieTrackerSpacingProvider provides spacing,
        CalorieTrackerPaddingProvider provides padding,
        IsDarkThemeProvider provides isDarkTheme,
        content = content
    )
}