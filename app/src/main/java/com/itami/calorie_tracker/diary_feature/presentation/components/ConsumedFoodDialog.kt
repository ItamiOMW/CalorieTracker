package com.itami.calorie_tracker.diary_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.components.NutrientAmountItem
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import kotlin.math.roundToInt

@Composable
fun ConsumedFoodDialog(
    consumedFood: ConsumedFood,
    modifier: Modifier = Modifier,
    containerColor: Color = CalorieTrackerTheme.colors.surfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    tonalElevation: Dp = 5.dp,
    contentPadding: PaddingValues = PaddingValues(
        start = CalorieTrackerTheme.padding.default,
        end = CalorieTrackerTheme.padding.default,
        top = CalorieTrackerTheme.padding.default,
        bottom = CalorieTrackerTheme.padding.small
    ),
    cancelText: String = stringResource(id = R.string.cancel),
    confirmText: String = stringResource(id = R.string.OK),
    onConfirm: (weightGrams: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val weightText = rememberSaveable {
        mutableStateOf(consumedFood.grams.toString())
    }

    val calories = remember(weightText.value) {
        val weight = weightText.value.toIntOrNull() ?: 0
        (weight / 100f * consumedFood.food.caloriesIn100Grams).roundToInt()
    }

    val fats = remember(weightText.value) {
        val weight = weightText.value.toIntOrNull() ?: 0
        (weight / 100f * consumedFood.food.fatsIn100Grams).roundToInt()
    }

    val proteins = remember(weightText.value) {
        val weight = weightText.value.toIntOrNull() ?: 0
        (weight / 100f * consumedFood.food.proteinsIn100Grams).roundToInt()
    }

    val carbs = remember(weightText.value) {
        val weight = weightText.value.toIntOrNull() ?: 0
        (weight / 100f * consumedFood.food.carbsIn100Grams).roundToInt()
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = consumedFood.food.name,
                    style = CalorieTrackerTheme.typography.titleMedium,
                    color = CalorieTrackerTheme.colors.onSurfacePrimary
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                NutrientsAmountSection(
                    modifier = Modifier.fillMaxWidth(),
                    calories = calories,
                    proteins = proteins,
                    fats = fats,
                    carbs = carbs
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
                GramsTextField(
                    modifier = Modifier
                        .padding(start = CalorieTrackerTheme.padding.small)
                        .fillMaxWidth(0.6f),
                    weightText = weightText.value,
                    onWeightChange = { newValue ->
                        weightText.value = newValue
                    }
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                ConfirmationSection(
                    modifier = Modifier.align(Alignment.End),
                    cancelText = cancelText,
                    confirmText = confirmText,
                    onConfirm = {
                        val weightGrams = weightText.value.toIntOrNull() ?: Constants.DEFAULT_FOOD_SERVING_GRAMS
                        onConfirm(weightGrams)
                    },
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
private fun GramsTextField(
    modifier: Modifier,
    weightText: String,
    onWeightChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            modifier = Modifier.weight(1f, true),
            value = weightText,
            onValueChange = { newValue ->
                val newText = newValue.takeIf { input ->
                    input.isEmpty() || input.toIntOrNull() != null
                }
                newText?.let(onWeightChange)
            },
            textStyle = CalorieTrackerTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = CalorieTrackerTheme.colors.primary,
                unfocusedIndicatorColor = CalorieTrackerTheme.colors.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = CalorieTrackerTheme.colors.primary,
                unfocusedTextColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                focusedTextColor = CalorieTrackerTheme.colors.onSurfacePrimary,
            ),
            label = {},
            maxLines = 1,
        )
        Text(
            modifier = Modifier.weight(1f, false),
            text = stringResource(R.string.grams),
            color = CalorieTrackerTheme.colors.primary,
            style = CalorieTrackerTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun NutrientsAmountSection(
    modifier: Modifier,
    calories: Int,
    proteins: Int,
    fats: Int,
    carbs: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NutrientAmountItem(
            modifier = Modifier.weight(1f),
            nutrientName = stringResource(id = R.string.nutrient_name_calories),
            nutrientAmount = calories.toString()
        )
        NutrientAmountItem(
            modifier = Modifier.weight(1f),
            nutrientName = stringResource(id = R.string.nutrient_name_proteins),
            nutrientAmount = stringResource(id = R.string.amount_grams, proteins)
        )
        NutrientAmountItem(
            modifier = Modifier.weight(1f),
            nutrientName = stringResource(id = R.string.nutrient_name_fats),
            nutrientAmount = stringResource(id = R.string.amount_grams, fats)
        )
        NutrientAmountItem(
            modifier = Modifier.weight(1f),
            nutrientName = stringResource(id = R.string.nutrient_name_carbs),
            nutrientAmount = stringResource(id = R.string.amount_grams, carbs)
        )
    }
}

@Composable
private fun ConfirmationSection(
    modifier: Modifier,
    cancelText: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = onDismiss
        ) {
            Text(
                text = cancelText,
                style = CalorieTrackerTheme.typography.labelLarge,
                color = CalorieTrackerTheme.colors.primary,
                maxLines = 1
            )
        }
        TextButton(
            onClick = onConfirm
        ) {
            Text(
                text = confirmText,
                style = CalorieTrackerTheme.typography.labelLarge,
                color = CalorieTrackerTheme.colors.primary,
                maxLines = 1
            )
        }
    }
}