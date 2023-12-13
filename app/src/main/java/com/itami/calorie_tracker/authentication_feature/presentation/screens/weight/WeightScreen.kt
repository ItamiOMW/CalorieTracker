package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun WeightScreen(
    onNavigateToAge: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    state: WeightState,
    uiEvent: Flow<WeightUiEvent>,
    onEvent: (WeightEvent) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                WeightUiEvent.WeightSaved -> {
                    onNavigateToAge()
                }

                is WeightUiEvent.ShowSnackbar -> {
                    onShowSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            TopSection(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(
                        top = CalorieTrackerTheme.padding.large,
                        start = CalorieTrackerTheme.padding.large,
                        end = CalorieTrackerTheme.padding.large
                    )
            )
            WeightSection(
                weight = state.weight,
                onValueChange = { weight ->
                    onEvent(WeightEvent.WeightValueChange(weight))
                },
                weightUnit = { state.selectedWeightUnit },
                onChangeWeightUnit = { weightUnit ->
                    onEvent(WeightEvent.ChangeWeightUnit(weightUnit))
                },
                modifier = Modifier
                    .widthIn(max = 200.dp)
                    .padding(start = CalorieTrackerTheme.padding.medium)
            )
            BottomSection(
                onFABClick = {
                    onEvent(WeightEvent.SaveWeight)
                },
                onNavigateBack = onNavigateBack,
                isLoading = state.isLoading,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        start = CalorieTrackerTheme.padding.medium,
                        end = CalorieTrackerTheme.padding.medium,
                        bottom = CalorieTrackerTheme.padding.large
                    )
            )
        }
    }
}

@Composable
private fun TopSection(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.weight_screen_title),
            color = CalorieTrackerTheme.colors.onBackground,
            style = CalorieTrackerTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
        Text(
            text = stringResource(R.string.weight_screen_description),
            color = CalorieTrackerTheme.colors.onBackgroundVariant,
            style = CalorieTrackerTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WeightSection(
    weight: String,
    onValueChange: (weight: String) -> Unit,
    weightUnit: () -> WeightUnit,
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
                text = when (weightUnit()) {
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
                    expanded = dropdownMenuExpanded,
                    onDismissRequest = {
                        dropdownMenuExpanded = false
                    },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.weight_unit_kilograms),
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
                        text = {
                            Text(
                                text = stringResource(R.string.weight_unit_pounds),
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

@Composable
private fun BottomSection(
    onFABClick: () -> Unit,
    onNavigateBack: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            modifier = Modifier.align(Alignment.Bottom),
            onClick = {
                onNavigateBack()
            }
        ) {
            Text(
                text = stringResource(R.string.back),
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                style = CalorieTrackerTheme.typography.bodyLarge,
            )
        }
        FloatingActionButton(
            containerColor = CalorieTrackerTheme.colors.primary,
            contentColor = CalorieTrackerTheme.colors.onPrimary,
            onClick = {
                if (!isLoading) {
                    onFABClick()
                }
            },
            modifier = Modifier
                .size(56.dp)
                .shadow(elevation = 5.dp, shape = CircleShape)
                .clip(CircleShape),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_navigate_next),
                contentDescription = stringResource(R.string.desc_icon_navigate_next),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}