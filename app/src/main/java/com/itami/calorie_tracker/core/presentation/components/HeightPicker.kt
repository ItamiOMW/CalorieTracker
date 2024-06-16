package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.Constants

@Composable
fun HeightPicker(
    centimeters: Int,
    heightUnit: HeightUnit,
    onValueChange: (centimeters: Int) -> Unit,
    onHeightUnitChange: (heightUnit: HeightUnit) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (heightUnit) {
        HeightUnit.FEET -> {
            FeetHeightPicker(
                centimeters = centimeters,
                onValueChange = onValueChange,
                onHeightUnitChange = onHeightUnitChange,
                modifier = modifier
            )
        }

        HeightUnit.METER -> {
            MeterHeightPicker(
                centimeters = centimeters,
                onValueChange = onValueChange,
                onHeightUnitChange = onHeightUnitChange,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun MeterHeightPicker(
    centimeters: Int,
    onValueChange: (centimeters: Int) -> Unit,
    onHeightUnitChange: (heightUnit: HeightUnit) -> Unit,
    modifier: Modifier,
) {
    val heightValueMeter = remember(centimeters) {
        HeightUnit.METER.centimetersToMeters(centimeters)
    }

    var dropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScrollablePicker(
                modifier = Modifier,
                value = heightValueMeter.meters,
                onValueChange = { meters ->
                    val newHeightValue = heightValueMeter.copy(meters = meters)
                    onValueChange(HeightUnit.FEET.metersToCentimeters(newHeightValue))
                },
                list = (Constants.MIN_HEIGHT_METERS..Constants.MAX_HEIGHT_METERS).toList(),
            )
            Text(
                text = stringResource(R.string.height_unit_short_meter),
                color = CalorieTrackerTheme.colors.primary,
                style = CalorieTrackerTheme.typography.bodyLarge,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScrollablePicker(
                modifier = Modifier,
                value = heightValueMeter.centimeters,
                onValueChange = { centimeters ->
                    val newHeightValue = heightValueMeter.copy(centimeters = centimeters)
                    onValueChange(HeightUnit.METER.metersToCentimeters(newHeightValue))
                },
                list = (Constants.MIN_CM_PER_METER..Constants.MAX_CM_PER_METER).toList(),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.none)
            ) {
                Text(
                    text = stringResource(R.string.height_unit_short_cm),
                    color = CalorieTrackerTheme.colors.primary,
                    style = CalorieTrackerTheme.typography.bodyLarge,
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {
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
                    }
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
                                    text = stringResource(R.string.height_unit_meter),
                                    style = CalorieTrackerTheme.typography.bodyLarge,
                                )
                            },
                            onClick = {
                                onHeightUnitChange(HeightUnit.METER)
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
                                    text = stringResource(R.string.height_unit_feet),
                                    style = CalorieTrackerTheme.typography.bodyLarge,
                                )
                            },
                            onClick = {
                                onHeightUnitChange(HeightUnit.FEET)
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
}

@Composable
private fun FeetHeightPicker(
    centimeters: Int,
    onValueChange: (centimeters: Int) -> Unit,
    onHeightUnitChange: (heightUnit: HeightUnit) -> Unit,
    modifier: Modifier,
) {
    val heightValueFeet = remember(centimeters) {
        HeightUnit.FEET.centimetersToFeet(centimeters)
    }

    var dropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.none)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScrollablePicker(
                modifier = Modifier,
                value = heightValueFeet.feet,
                onValueChange = { feet ->
                    val newHeightValue = heightValueFeet.copy(feet = feet)
                    onValueChange(HeightUnit.FEET.feetToCentimeters(newHeightValue))
                },
                list = (Constants.MIN_HEIGHT_FEET..Constants.MAX_HEIGHT_FEET).toList(),
            )
            Text(
                text = stringResource(R.string.height_unit_feet),
                color = CalorieTrackerTheme.colors.primary,
                style = CalorieTrackerTheme.typography.bodyLarge,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScrollablePicker(
                modifier = Modifier,
                value = heightValueFeet.inches,
                onValueChange = { inches ->
                    val newHeightValue = heightValueFeet.copy(inches = inches)
                    onValueChange(HeightUnit.FEET.feetToCentimeters(newHeightValue))
                },
                list = (Constants.MIN_INCHES..Constants.MAX_INCHES).toList(),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.height_unit_inches),
                    color = CalorieTrackerTheme.colors.primary,
                    style = CalorieTrackerTheme.typography.bodyLarge,
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {
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
                    }
                    DropdownMenu(
                        expanded = dropdownMenuExpanded,
                        onDismissRequest = {
                            dropdownMenuExpanded = false
                        },
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.height_unit_meter),
                                    style = CalorieTrackerTheme.typography.bodyLarge,
                                )
                            },
                            onClick = {
                                onHeightUnitChange(HeightUnit.METER)
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                            ),
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.height_unit_feet),
                                    style = CalorieTrackerTheme.typography.bodyLarge,
                                )
                            },
                            onClick = {
                                onHeightUnitChange(HeightUnit.FEET)
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
}