package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    initialTheme: Theme = Theme.SYSTEM_THEME,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    tonalElevation: Dp = 5.dp,
    contentPadding: PaddingValues = PaddingValues(
        start = CalorieTrackerTheme.padding.small,
        end = CalorieTrackerTheme.padding.small,
        top = CalorieTrackerTheme.padding.small,
        bottom = CalorieTrackerTheme.padding.extraSmall
    ),
    labelText: String = stringResource(R.string.theme),
    labelStyle: TextStyle = CalorieTrackerTheme.typography.titleMedium,
    labelColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    confirmText: String = stringResource(R.string.save),
    confirmTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    confirmTextColor: Color = CalorieTrackerTheme.colors.primary,
    cancelText: String = stringResource(R.string.cancel),
    cancelTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    cancelTextColor: Color = CalorieTrackerTheme.colors.primary,
    themeTextColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    onConfirm: (theme: Theme) -> Unit,
    onDismiss: () -> Unit,
) {
    var theme by rememberSaveable { mutableStateOf(initialTheme) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = containerColor,
            tonalElevation = tonalElevation,
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = labelText,
                    style = labelStyle,
                    color = labelColor,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                Column(
                    modifier = Modifier.padding(end = CalorieTrackerTheme.padding.default),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
                ) {
                    SelectableItem(
                        unitText = stringResource(id = R.string.theme_light),
                        unitTextColor = themeTextColor,
                        selected = theme == Theme.LIGHT_THEME,
                        onClick = { theme = Theme.LIGHT_THEME }
                    )
                    SelectableItem(
                        unitText = stringResource(id = R.string.theme_dark),
                        unitTextColor = themeTextColor,
                        selected = theme == Theme.DARK_THEME,
                        onClick = { theme = Theme.DARK_THEME }
                    )
                    SelectableItem(
                        unitText = stringResource(id = R.string.theme_system),
                        unitTextColor = themeTextColor,
                        selected = theme == Theme.SYSTEM_THEME,
                        onClick = { theme = Theme.SYSTEM_THEME }
                    )
                }
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = cancelText,
                            style = cancelTextStyle,
                            color = cancelTextColor,
                            maxLines = 1
                        )
                    }
                    TextButton(
                        onClick = { onConfirm(theme) }
                    ) {
                        Text(
                            text = confirmText,
                            style = confirmTextStyle,
                            color = confirmTextColor,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectableItem(
    unitText: String,
    unitTextColor: Color,
    selected: Boolean,
    radioButtonColors: RadioButtonColors = RadioButtonDefaults.colors(
        selectedColor = CalorieTrackerTheme.colors.primary,
        unselectedColor = CalorieTrackerTheme.colors.onSurfacePrimary,
    ),
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable { onClick()},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
    ) {
        RadioButton(
            selected = selected,
            colors = radioButtonColors,
            onClick = onClick,
        )
        Text(
            text = unitText,
            style = CalorieTrackerTheme.typography.bodyLarge,
            color = unitTextColor,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun ThemeDialogPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ThemeDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}