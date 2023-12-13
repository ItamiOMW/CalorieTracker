package com.itami.calorie_tracker.diary_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import kotlin.math.roundToInt

@Composable
fun ConsumedFoodComponent(
    consumedFood: ConsumedFood,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
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
    val showDropdown = rememberSaveable {
        mutableStateOf(false)
    }

    val calories = remember(consumedFood) {
        (consumedFood.grams / 100f * consumedFood.food.caloriesIn100Grams).roundToInt()
    }

    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        color = containerColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(weight = 1f, fill = false),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
            ) {
                Text(
                    text = consumedFood.food.name,
                    style = foodNameTextStyle,
                    color = contentColor,
                )
                Text(
                    text = stringResource(
                        R.string.grams_and_calories,
                        consumedFood.grams,
                        calories
                    ),
                    style = caloriesTextStyle,
                    color = secondaryContentColor,
                )
            }
            IconButton(
                onClick = {
                    showDropdown.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_more_vert),
                    contentDescription = stringResource(R.string.icon_desc_more_options),
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
                DropdownMenu(
                    modifier = Modifier
                        .background(CalorieTrackerTheme.colors.surfacePrimary),
                    expanded = showDropdown.value,
                    onDismissRequest = {
                        showDropdown.value = false
                    },
                ) {
                    DropdownMenuItem(
                        colors = MenuDefaults.itemColors(
                            textColor = CalorieTrackerTheme.colors.onSurfacePrimary
                        ),
                        text = {
                            Text(
                                text = stringResource(R.string.edit),
                                style = CalorieTrackerTheme.typography.bodySmall,
                            )
                        },
                        onClick = {
                            showDropdown.value = false
                            onEdit()
                        }
                    )
                    DropdownMenuItem(
                        colors = MenuDefaults.itemColors(
                            textColor = CalorieTrackerTheme.colors.onSurfacePrimary
                        ),
                        text = {
                            Text(
                                text = stringResource(R.string.delete),
                                style = CalorieTrackerTheme.typography.bodySmall,
                            )
                        },
                        onClick = {
                            showDropdown.value = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}