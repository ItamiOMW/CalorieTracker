package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme


@Composable
fun WeightPicker(
    weight: String,
    onValueChange: (weight: String) -> Unit,
    weightUnit: WeightUnit,
    onChangeWeightUnit: (WeightUnit) -> Unit,
    modifier: Modifier,
) {
    var dropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Top),
            value = weight,
            onValueChange = { newValue ->
                val newText = newValue.takeIf { input ->
                    input.isEmpty() || input.toFloatOrNull() != null
                }
                newText?.let { onValueChange(it) }
            },
            textStyle = CalorieTrackerTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedTextColor = CalorieTrackerTheme.colors.onBackground,
                unfocusedTextColor = CalorieTrackerTheme.colors.onBackground,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = CalorieTrackerTheme.colors.primary,
                focusedIndicatorColor = CalorieTrackerTheme.colors.primary,
                unfocusedIndicatorColor = CalorieTrackerTheme.colors.primary,
            ),
            label = { },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
        )
        Row(
            modifier = Modifier.align(Alignment.Bottom),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.none)
        ) {
            Text(
                text = when (weightUnit) {
                    WeightUnit.POUND -> stringResource(id = R.string.weight_unit_short_pounds)
                    WeightUnit.KILOGRAM -> stringResource(id = R.string.weight_unit_short_kilograms)
                },
                color = CalorieTrackerTheme.colors.primary,
                style = CalorieTrackerTheme.typography.bodyLarge,
            )
            IconButton(
                onClick = {
                    dropdownMenuExpanded = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_drop_down),
                    contentDescription = stringResource(R.string.icon_desc_arrow_drop_down),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
                DropdownMenu(
                    modifier = Modifier.background(CalorieTrackerTheme.colors.surfacePrimary),
                    expanded = dropdownMenuExpanded,
                    onDismissRequest = {
                        dropdownMenuExpanded = false
                    },
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.background(CalorieTrackerTheme.colors.surfacePrimary),
                        text = {
                            Text(
                                text = stringResource(R.string.weight_unit_short_kilograms),
                                style = CalorieTrackerTheme.typography.bodyLarge,
                            )
                        },
                        onClick = {
                            onChangeWeightUnit(WeightUnit.KILOGRAM)
                            dropdownMenuExpanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                        ),
                    )
                    DropdownMenuItem(
                        modifier = Modifier.background(CalorieTrackerTheme.colors.surfacePrimary),
                        text = {
                            Text(
                                text = stringResource(R.string.weight_unit_short_pounds),
                                style = CalorieTrackerTheme.typography.bodyLarge,
                            )
                        },
                        onClick = {
                            onChangeWeightUnit(WeightUnit.POUND)
                            dropdownMenuExpanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                        ),
                    )
                }
            }
        }
    }
}