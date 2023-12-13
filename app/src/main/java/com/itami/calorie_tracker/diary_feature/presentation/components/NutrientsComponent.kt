package com.itami.calorie_tracker.diary_feature.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun NutrientsComponent(
    proteinsConsumed: Int,
    fatsConsumed: Int,
    carbsConsumed: Int,
    caloriesConsumed: Int,
    proteinsGoal: Int,
    fatsGoal: Int,
    carbsGoal: Int,
    caloriesGoal: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    contentColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    indicatorTrackColor: Color = CalorieTrackerTheme.colors.surfaceVariant,
    proteinsColor: Color = CalorieTrackerTheme.colors.red,
    fatsColor: Color = CalorieTrackerTheme.colors.orange,
    carbsColor: Color = CalorieTrackerTheme.colors.lightGreen,
    caloriesColor: Color = CalorieTrackerTheme.colors.green,
    shape: Shape = CalorieTrackerTheme.shapes.small,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = CalorieTrackerTheme.padding.small,
                    vertical = CalorieTrackerTheme.padding.default,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.medium)
            ) {
                NutrientStatisticItem(
                    nutrientName = stringResource(R.string.nutrient_name_proteins),
                    consumedAmount = proteinsConsumed,
                    goalAmount = proteinsGoal,
                    textColor = contentColor,
                    indicatorColor = proteinsColor,
                    indicatorTrackColor = indicatorTrackColor,
                    modifier = Modifier.weight(1f)
                )
                NutrientStatisticItem(
                    nutrientName = stringResource(R.string.nutrient_name_fats),
                    consumedAmount = fatsConsumed,
                    goalAmount = fatsGoal,
                    textColor = contentColor,
                    indicatorColor = fatsColor,
                    indicatorTrackColor = indicatorTrackColor,
                    modifier = Modifier.weight(1f)
                )
                NutrientStatisticItem(
                    nutrientName = stringResource(R.string.nutrient_name_carbs),
                    consumedAmount = carbsConsumed,
                    goalAmount = carbsGoal,
                    textColor = contentColor,
                    indicatorColor = carbsColor,
                    indicatorTrackColor = indicatorTrackColor,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
            NutrientStatisticItem(
                nutrientName = stringResource(R.string.nutrient_name_calories),
                consumedAmount = caloriesConsumed,
                goalAmount = caloriesGoal,
                textColor = contentColor,
                indicatorColor = caloriesColor,
                indicatorTrackColor = indicatorTrackColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun NutrientStatisticItem(
    nutrientName: String,
    consumedAmount: Int,
    goalAmount: Int,
    textColor: Color,
    indicatorColor: Color,
    indicatorTrackColor: Color,
    modifier: Modifier = Modifier,
) {
    val percentage = remember(consumedAmount, goalAmount) {
        (consumedAmount.toFloat() / goalAmount.toFloat())
    }

    val animatedPercentage by animateFloatAsState(
        targetValue = percentage,
        label = "Animated percentage"
    )

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                id = R.string.consumed_to_total_amount,
                consumedAmount,
                goalAmount
            ),
            style = CalorieTrackerTheme.typography.bodySmall,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraSmall))
        LinearProgressIndicator(
            progress = if (animatedPercentage > 1f) 1f else animatedPercentage,
            color = indicatorColor,
            trackColor = indicatorTrackColor,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraSmall))
        Text(
            text = nutrientName,
            style = CalorieTrackerTheme.typography.bodyMedium,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}