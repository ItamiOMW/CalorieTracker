package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun IconToText(
    modifier: Modifier = Modifier,
    text: String,
    iconPainter: Painter,
    iconColor: Color = CalorieTrackerTheme.colors.onBackground,
    iconSize: Dp = 24.dp,
    textColor: Color = iconColor,
    textStyle: TextStyle = CalorieTrackerTheme.typography.bodyLarge,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}