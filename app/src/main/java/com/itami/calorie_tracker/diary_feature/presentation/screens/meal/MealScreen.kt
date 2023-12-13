package com.itami.calorie_tracker.diary_feature.presentation.screens.meal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.components.DialogComponent
import com.itami.calorie_tracker.core.presentation.navigation.NavResultCallback
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.presentation.components.ConsumedFoodComponent
import com.itami.calorie_tracker.diary_feature.presentation.components.ConsumedFoodDialog
import com.itami.calorie_tracker.diary_feature.presentation.components.NutrientAmountItem
import kotlinx.coroutines.flow.Flow
import kotlin.math.roundToInt

@Composable
fun MealScreen(
    onNavigateSearchFood: (NavResultCallback<ConsumedFood?>) -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    state: MealState,
    uiEvent: Flow<MealUiEvent>,
    onEvent: (event: MealEvent) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is MealUiEvent.MealSaved -> {
                    onNavigateBack()
                }

                is MealUiEvent.ShowSnackbar -> {
                    onShowSnackbar(event.message)
                }
            }
        }
    }

    BackHandler {
        onEvent(MealEvent.ShowExitDialog(true))
    }

    if (state.showExitDialog) {
        DialogComponent(
            title = stringResource(R.string.exit),
            description = stringResource(R.string.exit_warning),
            cancelText = stringResource(R.string.cancel),
            confirmText = stringResource(R.string.exit),
            onConfirm = {
                onEvent(MealEvent.ShowExitDialog(false))
                onNavigateBack()
            },
            onDismiss = {
                onEvent(MealEvent.ShowExitDialog(false))
            }
        )
    }

    if (state.selectedConsumedFoodIndex != null) {
        val index = state.selectedConsumedFoodIndex
        state.consumedFoods.getOrNull(index)?.let { consumedFood ->
            ConsumedFoodDialog(
                consumedFood = consumedFood,
                onConfirm = { weightGrams ->
                    onEvent(
                        MealEvent.UpdateConsumedFood(
                            index = index,
                            weightGrams = weightGrams
                        )
                    )
                },
                onDismiss = {
                    onEvent(MealEvent.SelectConsumedFood(null))
                }
            )
        }
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                mealName = state.mealName,
                onMealNameChange = { newValue ->
                    onEvent(MealEvent.MealNameChange(newValue))
                },
                consumedFoods = state.consumedFoods,
                onCloseIconClick = {
                    onEvent(MealEvent.ShowExitDialog(true))
                },
                onAddIconClick = {
                    onNavigateSearchFood { consumedFoodResult ->
                        consumedFoodResult?.let { consumedFood ->
                            onEvent(MealEvent.AddConsumedFood(consumedFood))
                        }
                    }
                },
            )
        },
        bottomBar = {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = CalorieTrackerTheme.padding.default,
                        end = CalorieTrackerTheme.padding.default,
                        bottom = CalorieTrackerTheme.padding.large
                    ),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = CalorieTrackerTheme.colors.primary,
                    contentColor = CalorieTrackerTheme.colors.onPrimary
                ),
                shape = CalorieTrackerTheme.shapes.small,
                contentPadding = PaddingValues(
                    vertical = CalorieTrackerTheme.padding.default
                ),
                onClick = {
                    onEvent(MealEvent.SaveMeal)
                },
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = CalorieTrackerTheme.typography.titleSmall,
                    color = CalorieTrackerTheme.colors.onPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            ConsumedFoodsSection(
                modifier = Modifier
                    .padding(top = CalorieTrackerTheme.padding.extraSmall)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                consumedFoods = state.consumedFoods,
                onConsumedFoodClick = { index ->
                    onEvent(MealEvent.SelectConsumedFood(index))
                },
                onEditConsumedFood = { index ->
                    onEvent(MealEvent.SelectConsumedFood(index))
                },
                onDeleteConsumedFood = { index ->
                    onEvent(MealEvent.DeleteConsumedFood(index))
                }
            )
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ConsumedFoodsSection(
    modifier: Modifier,
    consumedFoods: List<ConsumedFood>,
    onConsumedFoodClick: (index: Int) -> Unit,
    onEditConsumedFood: (index: Int) -> Unit,
    onDeleteConsumedFood: (index: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items = consumedFoods) { index, consumedFood ->
            ConsumedFoodComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
                consumedFood = consumedFood,
                onClick = {
                    onConsumedFoodClick(index)
                },
                onEdit = {
                    onEditConsumedFood(index)
                },
                onDelete = {
                    onDeleteConsumedFood(index)
                }
            )
        }
    }
}

@Composable
private fun TopBarSection(
    onCloseIconClick: () -> Unit,
    onAddIconClick: () -> Unit,
    mealName: String,
    onMealNameChange: (String) -> Unit,
    consumedFoods: List<ConsumedFood>,
) {
    val calories = remember(consumedFoods) {
        consumedFoods.sumOf { (it.grams / 100f * it.food.caloriesIn100Grams).roundToInt() }
    }

    val proteins = remember(consumedFoods) {
        consumedFoods.sumOf { (it.grams / 100f * it.food.proteinsIn100Grams).roundToInt() }
    }

    val fats = remember(consumedFoods) {
        consumedFoods.sumOf { (it.grams / 100f * it.food.fatsIn100Grams).roundToInt() }
    }

    val carbs = remember(consumedFoods) {
        consumedFoods.sumOf { (it.grams / 100f * it.food.carbsIn100Grams).roundToInt() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CalorieTrackerTheme.padding.default),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.weight(weight = 0.3f, fill = true),
                onClick = onCloseIconClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_close),
                    contentDescription = stringResource(R.string.desc_icon_close),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f, fill = true),
                value = mealName,
                onValueChange = onMealNameChange,
                textStyle = CalorieTrackerTheme.typography.titleSmall.copy(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = CalorieTrackerTheme.colors.onBackground,
                    unfocusedTextColor = CalorieTrackerTheme.colors.onBackground,
                    focusedPlaceholderColor = CalorieTrackerTheme.colors.onBackgroundVariant,
                    unfocusedPlaceholderColor = CalorieTrackerTheme.colors.onBackgroundVariant,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = CalorieTrackerTheme.colors.primary
                ),
                maxLines = 1,
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.hint_meal_name),
                        style = CalorieTrackerTheme.typography.bodyLarge,
                        color = CalorieTrackerTheme.colors.onBackgroundVariant,
                        textAlign = TextAlign.Center
                    )
                },
            )
            IconButton(
                modifier = Modifier.weight(weight = 0.3f, fill = true),
                onClick = onAddIconClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = stringResource(R.string.desc_icon_add),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
        Row(
            modifier = Modifier
                .padding(horizontal = CalorieTrackerTheme.padding.default)
                .fillMaxWidth(),
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
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
        Divider(modifier = Modifier.fillMaxWidth(), color = CalorieTrackerTheme.colors.outline)
    }
}
