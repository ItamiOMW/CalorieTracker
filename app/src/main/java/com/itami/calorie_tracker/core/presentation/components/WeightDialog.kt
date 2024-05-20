package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import java.util.Locale

typealias WeightGramsInt = Int

@Composable
fun WeightDialog(
    modifier: Modifier = Modifier,
    selectedWeightUnit: WeightUnit = WeightUnit.KILOGRAM,
    startWeightGrams: Int? = null,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    tonalElevation: Dp = 5.dp,
    contentPadding: PaddingValues = PaddingValues(
        start = CalorieTrackerTheme.padding.small,
        end = CalorieTrackerTheme.padding.small,
        top = CalorieTrackerTheme.padding.small,
        bottom = CalorieTrackerTheme.padding.extraSmall
    ),
    labelText: String = stringResource(R.string.label_weight),
    labelStyle: TextStyle = CalorieTrackerTheme.typography.titleMedium,
    labelColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    confirmText: String = stringResource(R.string.add),
    confirmTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    confirmTextColor: Color = CalorieTrackerTheme.colors.primary,
    cancelText: String = stringResource(R.string.cancel),
    cancelTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    cancelTextColor: Color = CalorieTrackerTheme.colors.primary,
    onShowErrorMessage: (error: String) -> Unit,
    onChangeWeightUnit: (weightUnit: WeightUnit) -> Unit,
    onConfirm: (weightGrams: WeightGramsInt) -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current

    var weightString by rememberSaveable {
        val startValue = selectedWeightUnit.convert(startWeightGrams ?: 0)
        mutableStateOf(String.format(Locale.US, "%.1f", startValue))
    }

    UpdateEffect(selectedWeightUnit) {
        weightString = if (weightString.toFloatOrNull() == null) {
            "0.0"
        } else {
            when(selectedWeightUnit) {
                WeightUnit.POUND -> {
                    val weight = WeightUnit.KILOGRAM.kilogramsToPounds(weightString.toFloat())
                    val formattedWeight = String.format(Locale.US, "%.1f", weight,)
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

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = containerColor,
            tonalElevation = tonalElevation,
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = labelText,
                    style = labelStyle,
                    color = labelColor,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                WeightPicker(
                    weight = weightString,
                    onValueChange = { weightString = it },
                    weightUnit = selectedWeightUnit,
                    onChangeWeightUnit = onChangeWeightUnit,
                    modifier = Modifier
                        .widthIn(max = 200.dp)
                        .padding(start = CalorieTrackerTheme.padding.medium)
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = cancelText,
                            style = cancelTextStyle,
                            color = cancelTextColor,
                            maxLines = 1
                        )
                    }
                    TextButton(
                        onClick = {
                            if (weightString.toFloatOrNull() == null) {
                                onShowErrorMessage(context.getString(R.string.error_invalid_weight))
                            } else {
                                onConfirm(selectedWeightUnit.toGrams(weightString.toFloat()))
                            }
                        }
                    ) {
                        Text(
                            text = confirmText,
                            style = confirmTextStyle,
                            color = confirmTextColor,
                            maxLines = 1
                        )
                    }
                }
            }

        }
    }
}