package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun NutrientAmountItem(
    modifier: Modifier = Modifier,
    nutrientName: String,
    nutrientAmount: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nutrientAmount,
            style = CalorieTrackerTheme.typography.titleSmall,
            color = CalorieTrackerTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        Text(
            text = nutrientName,
            style = CalorieTrackerTheme.typography.bodyMedium,
            color = CalorieTrackerTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
    }
}