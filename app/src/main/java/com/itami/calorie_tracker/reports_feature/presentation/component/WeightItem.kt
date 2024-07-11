package com.itami.calorie_tracker.reports_feature.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.model.WeightUnit.KILOGRAM
import com.itami.calorie_tracker.core.domain.model.WeightUnit.POUND
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeightItem(
    weight: WeightUi,
    weightUnit: WeightUnit,
    modifier: Modifier = Modifier,
    surfaceColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    onSurfaceColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = surfaceColor,
        contentColor = onSurfaceColor,
        shape = shape,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = CalorieTrackerTheme.padding.small)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = weight.datetime.format(
                    DateTimeFormatter.ofPattern(
                        "MMM d, yyyy",
                        Locale.getDefault()
                    )
                ),
                style = CalorieTrackerTheme.typography.labelLarge,
            )
            Text(
                text = when(weightUnit) {
                    POUND -> {
                        stringResource(id = R.string.pounds_amount, weightUnit.convert(weight.weightGrams))
                    }
                    KILOGRAM -> {
                        stringResource(id = R.string.kilograms_amount, weightUnit.convert(weight.weightGrams))
                    }
                },
                style = CalorieTrackerTheme.typography.labelLarge,
            )
        }
    }
}