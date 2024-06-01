package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    error: String? = null,
    label: String? = null,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Done,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = CalorieTrackerTheme.colors.onBackground,
        unfocusedTextColor = CalorieTrackerTheme.colors.onBackgroundVariant,
        disabledTextColor = CalorieTrackerTheme.colors.onBackgroundVariant,
        focusedBorderColor = CalorieTrackerTheme.colors.primary,
        unfocusedBorderColor = CalorieTrackerTheme.colors.outline,
        disabledBorderColor = CalorieTrackerTheme.colors.outline,
        cursorColor = CalorieTrackerTheme.colors.primary,
        focusedLabelColor = CalorieTrackerTheme.colors.primary,
        unfocusedLabelColor = CalorieTrackerTheme.colors.onBackgroundVariant,
        disabledLabelColor = CalorieTrackerTheme.colors.onBackgroundVariant,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    ),
    errorColor: Color = CalorieTrackerTheme.colors.red,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = error != null) {
            error?.let { error ->
                Text(
                    text = error,
                    color = errorColor,
                    style = CalorieTrackerTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = CalorieTrackerTheme.padding.extraSmall)
                )
            }
        }
        androidx.compose.material3.OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            colors = colors,
            singleLine = singleLine,
            enabled = enabled,
            shape = shape,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation = visualTransformation,
            label = {
                if (label != null) {
                    Text(
                        text = label,
                        style = CalorieTrackerTheme.typography.labelLarge,
                    )
                }
            },
            trailingIcon = trailingIcon
        )
    }
}