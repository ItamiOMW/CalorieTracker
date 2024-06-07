package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ListStyleText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = CalorieTrackerTheme.typography.bodyLarge,
    color: Color =  CalorieTrackerTheme.colors.onBackground,
    textAlign: TextAlign = TextAlign.Start
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
    ) {
        Text(text = "•")
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = style,
            color = color,
            textAlign = textAlign
        )
    }
}