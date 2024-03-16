package com.itami.calorie_tracker.diary_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.diary_feature.presentation.model.MealUi
import java.time.format.DateTimeFormatter

@Composable
fun MealItem(
    meal: MealUi,
    onMealClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    contentColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    secondaryContentColor: Color = CalorieTrackerTheme.colors.onSurfaceVariant,
    shape: Shape = CalorieTrackerTheme.shapes.small,
) {
    Surface(
        modifier = modifier,
        onClick = onMealClick,
        color = containerColor,
        contentColor = contentColor,
        shape = shape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(CalorieTrackerTheme.padding.default),
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = meal.name,
                    style = CalorieTrackerTheme.typography.bodyMedium,
                    color = contentColor,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_schedule),
                        contentDescription = stringResource(R.string.desc_clock_icon),
                        tint = secondaryContentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = meal.createdAt.format(DateTimeFormatter.ofPattern(DateTimeUtil.DEFAULT_TIME_PATTERN)),
                        style = CalorieTrackerTheme.typography.bodySmall,
                        color = secondaryContentColor,
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.cal_amount, meal.calories),
                style = CalorieTrackerTheme.typography.titleSmall,
                color = contentColor,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}