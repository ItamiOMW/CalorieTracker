package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.Constants

@Composable
fun WaterServingSizeDialog(
    modifier: Modifier = Modifier,
    initialSizeMl: Int = Constants.DEFAULT_WATER_SERVING_ML,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    tonalElevation: Dp = 5.dp,
    contentPadding: PaddingValues = PaddingValues(
        start = CalorieTrackerTheme.padding.small,
        end = CalorieTrackerTheme.padding.small,
        top = CalorieTrackerTheme.padding.small,
        bottom = CalorieTrackerTheme.padding.extraSmall
    ),
    labelText: String = stringResource(R.string.water_serving_size),
    labelStyle: TextStyle = CalorieTrackerTheme.typography.titleMedium,
    labelColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    confirmText: String = stringResource(R.string.save),
    confirmTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    confirmTextColor: Color = CalorieTrackerTheme.colors.primary,
    cancelText: String = stringResource(R.string.cancel),
    cancelTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    cancelTextColor: Color = CalorieTrackerTheme.colors.primary,
    onConfirm: (ml: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var sizeMl by rememberSaveable { mutableIntStateOf(initialSizeMl) }

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
                TextField(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    value = sizeMl.toString(),
                    onValueChange = { newValue ->
                        sizeMl = newValue.toIntOrNull()?.coerceAtLeast(0) ?: 0
                    },
                    textStyle = CalorieTrackerTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = CalorieTrackerTheme.colors.onBackground,
                        unfocusedTextColor = CalorieTrackerTheme.colors.onBackground,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = CalorieTrackerTheme.colors.primary,
                        focusedIndicatorColor = CalorieTrackerTheme.colors.primary,
                        unfocusedIndicatorColor = CalorieTrackerTheme.colors.primary,
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.ml),
                            color = CalorieTrackerTheme.colors.onSurfacePrimary,
                            style = CalorieTrackerTheme.typography.bodySmall
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                )
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
                        onClick = { onConfirm(sizeMl) }
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