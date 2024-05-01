package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun SelectableButton(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    outlineColor: Color = CalorieTrackerTheme.colors.primary,
    containerColor: Color = CalorieTrackerTheme.colors.primary,
    contentColor: Color = CalorieTrackerTheme.colors.onPrimary,
    textStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = if (selected) containerColor else Color.Transparent,
        contentColor = contentColor,
        border = if (!selected) BorderStroke(1.dp, outlineColor) else null,
        shape = CalorieTrackerTheme.shapes.extraLarge,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center,
            color = if (selected) contentColor else outlineColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = CalorieTrackerTheme.padding.extraSmall)
        )
    }
}