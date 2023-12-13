package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun VerticalProgressBar(
    progress: Float, // 0.0f == 0% --> 1.0f == 100%
    text: String? = null,
    modifier: Modifier,
    indicatorBrush: Brush,
    indicatorBackgroundColor: Color,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    textStyle: TextStyle = CalorieTrackerTheme.typography.bodySmall,
    textColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(color = indicatorBackgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(progress)
                .background(brush = indicatorBrush)
        )
        if (text != null) {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}