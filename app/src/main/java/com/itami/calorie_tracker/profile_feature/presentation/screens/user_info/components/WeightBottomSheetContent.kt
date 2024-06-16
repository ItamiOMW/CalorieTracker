package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.components.UpdateEffect
import com.itami.calorie_tracker.core.presentation.components.WeightPicker
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.Constants
import java.util.Locale

@Composable
fun WeightBottomSheetContent(
    modifier: Modifier = Modifier,
    initialWeightGrams: Int = Constants.DEFAULT_WEIGHT_GRAMS,
    weightUnit: WeightUnit = WeightUnit.KILOGRAM,
    onWeightUnitChange: (weightUnit: WeightUnit) -> Unit,
    onConfirm: (weightGrams: Int) -> Unit,
) {
    var weightString by rememberSaveable {
        val startValue = weightUnit.convert(initialWeightGrams)
        mutableStateOf(String.format(Locale.US, "%.1f", startValue))
    }

    UpdateEffect(weightUnit) {
        weightString = if (weightString.toFloatOrNull() == null) {
            "0.0"
        } else {
            when (weightUnit) {
                WeightUnit.POUND -> {
                    val weight = WeightUnit.KILOGRAM.kilogramsToPounds(weightString.toFloat())
                    val formattedWeight = String.format(Locale.US, "%.1f", weight)
                    formattedWeight
                }

                WeightUnit.KILOGRAM -> {
                    val weight = WeightUnit.POUND.poundsToKilograms(weightString.toFloat())
                    val formattedWeight = String.format(Locale.US, "%.1f", weight)
                    formattedWeight
                }
            }
        }
    }


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.weight),
            style = CalorieTrackerTheme.typography.titleMedium,
            color = CalorieTrackerTheme.colors.onSurfacePrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        WeightPicker(
            weight = weightString,
            onValueChange = { weightString = it },
            weightUnit = weightUnit,
            onChangeWeightUnit = onWeightUnitChange,
            modifier = Modifier
                .widthIn(max = 200.dp)
                .padding(start = CalorieTrackerTheme.padding.medium)
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = CalorieTrackerTheme.colors.primary,
                contentColor = CalorieTrackerTheme.colors.onPrimary,
            ),
            contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (weightString.toFloatOrNull() != null) {
                    onConfirm(weightUnit.toGrams(weightString.toFloat()))
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.done),
                style = CalorieTrackerTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}