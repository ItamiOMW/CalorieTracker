package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun DialogComponent(
    title: String,
    description: String,
    cancelText: String,
    confirmText: String,
    modifier: Modifier = Modifier,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    tonalElevation: Dp = 5.dp,
    contentPadding: PaddingValues = PaddingValues(
        start = CalorieTrackerTheme.padding.small,
        end = CalorieTrackerTheme.padding.small,
        top = CalorieTrackerTheme.padding.small,
        bottom = CalorieTrackerTheme.padding.extraSmall
    ),
    titleStyle: TextStyle = CalorieTrackerTheme.typography.titleMedium,
    descriptionStyle: TextStyle = CalorieTrackerTheme.typography.bodyLarge,
    confirmTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    cancelTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    titleColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    descriptionColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    confirmTextColor: Color = CalorieTrackerTheme.colors.primary,
    cancelTextColor: Color = CalorieTrackerTheme.colors.primary,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
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
            ) {
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                Text(
                    text = description,
                    style = descriptionStyle,
                    color = descriptionColor,
                    textAlign = TextAlign.Center,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
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
                        onClick = onConfirm
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