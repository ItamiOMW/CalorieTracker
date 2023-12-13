package com.itami.calorie_tracker.diary_feature.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.components.VerticalProgressBar
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.mlToFormattedLiters

@Composable
fun WaterIntakeComponent(
    consumedWaterMl: Int,
    waterMlGoal: Int,
    lastTimeDrank: String?,
    onAddWaterClick: () -> Unit,
    onRemoveWaterClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    contentColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    secondaryContentColor: Color = CalorieTrackerTheme.colors.onSurfaceVariant,
    buttonColor: Color = CalorieTrackerTheme.colors.surfaceVariant,
    onButtonColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    waterIndicatorBackgroundColor: Color = CalorieTrackerTheme.colors.surfaceVariant,
    waterIndicatorBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            CalorieTrackerTheme.colors.lighterBlue,
            CalorieTrackerTheme.colors.lightBlue
        )
    ),
    shape: Shape = CalorieTrackerTheme.shapes.small,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = shape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(CalorieTrackerTheme.padding.default),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                WaterConsumed(
                    consumedWaterMl = consumedWaterMl,
                    waterMlGoal = waterMlGoal,
                    textColor = contentColor
                )
                if (lastTimeDrank != null) {
                    LastTimeDrank(
                        color = secondaryContentColor,
                        lastTimeDrank = lastTimeDrank,
                    )
                }
            }
            WaterIndicator(
                consumedWaterMl = consumedWaterMl,
                waterMlGoal = waterMlGoal,
                onAddWaterClick = onAddWaterClick,
                onRemoveWaterClick = onRemoveWaterClick,
                buttonColor = buttonColor,
                onButtonColor = onButtonColor,
                waterIndicatorBrush = waterIndicatorBrush,
                waterIndicatorBackgroundColor = waterIndicatorBackgroundColor,
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun WaterIndicator(
    modifier: Modifier,
    consumedWaterMl: Int,
    waterMlGoal: Int,
    onAddWaterClick: () -> Unit,
    onRemoveWaterClick: () -> Unit,
    buttonColor: Color,
    onButtonColor: Color,
    waterIndicatorBrush: Brush,
    waterIndicatorBackgroundColor: Color,
) {
    val percentage = remember(consumedWaterMl, waterMlGoal) {
        (consumedWaterMl.toFloat() / waterMlGoal.toFloat())
    }

    val animatedPercentage by animateFloatAsState(
        targetValue = percentage,
        label = "Animated dp"
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.default)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = CalorieTrackerTheme.padding.small),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            FilledIconButton(
                modifier = Modifier.size(30.dp),
                onClick = onAddWaterClick,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = buttonColor),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = stringResource(R.string.desc_icon_add),
                    tint = onButtonColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            FilledIconButton(
                modifier = Modifier.size(30.dp),
                onClick = onRemoveWaterClick,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = buttonColor),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_remove),
                    contentDescription = stringResource(R.string.desc_icon_remove),
                    tint = onButtonColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        VerticalProgressBar(
            progress = if (animatedPercentage > 1f) 1f else animatedPercentage,
            text = stringResource(R.string.percentage, percentage * 100),
            indicatorBrush = waterIndicatorBrush,
            indicatorBackgroundColor = waterIndicatorBackgroundColor,
            shape = CalorieTrackerTheme.shapes.extraLarge,
            textColor = CalorieTrackerTheme.colors.onSurfacePrimary,
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp),
        )
    }
}

@Composable
private fun LastTimeDrank(
    color: Color,
    lastTimeDrank: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_schedule),
            contentDescription = stringResource(R.string.desc_clock_icon),
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = lastTimeDrank,
            style = CalorieTrackerTheme.typography.bodySmall,
            color = color,
        )
    }
}

@Composable
private fun WaterConsumed(
    consumedWaterMl: Int,
    waterMlGoal: Int,
    textColor: Color,
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.water),
            style = CalorieTrackerTheme.typography.bodySmall,
            color = textColor
        )
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "${consumedWaterMl.mlToFormattedLiters()} / ",
                color = textColor,
                style = CalorieTrackerTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.water_liters, waterMlGoal.mlToFormattedLiters()),
                color = textColor,
                style = CalorieTrackerTheme.typography.bodyMedium,
            )
        }
    }
}