package com.itami.calorie_tracker.diary_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.diary_feature.domain.model.Food

@Composable
fun FoodItem(
    food: Food,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    contentColor: Color = CalorieTrackerTheme.colors.onBackground,
    secondaryContentColor: Color = CalorieTrackerTheme.colors.onBackgroundVariant,
    foodNameTextStyle: TextStyle = CalorieTrackerTheme.typography.bodyMedium,
    caloriesTextStyle: TextStyle = CalorieTrackerTheme.typography.bodySmall,
    shape: Shape = CalorieTrackerTheme.shapes.none,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = CalorieTrackerTheme.padding.default,
        vertical = CalorieTrackerTheme.padding.small,
    ),
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        color = containerColor,
        contentColor = contentColor
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
        ) {
            Text(
                text = food.name,
                style = foodNameTextStyle,
                color = contentColor,
            )
            Text(
                text = stringResource(
                    R.string.cal_per_100_grams,
                    food.caloriesIn100Grams
                ),
                style = caloriesTextStyle,
                color = secondaryContentColor,
            )
        }
    }
}