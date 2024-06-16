package com.itami.calorie_tracker.profile_feature.presentation.screens.calorie_intake

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.UnderlinedFieldWithValue
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun CalorieIntakeScreen(
    onNavigateBack: () -> Unit,
    onCalorieIntakeSaved: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: CalorieIntakeViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is CalorieIntakeUiEvent.CalorieIntakeSaved -> onCalorieIntakeSaved()
            is CalorieIntakeUiEvent.NavigateBack -> onNavigateBack()
            is CalorieIntakeUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    CalorieIntakeScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun CalorieIntakeScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        CalorieIntakeScreenContent(
            state = CalorieIntakeState(),
            onAction = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalorieIntakeScreenContent(
    state: CalorieIntakeState,
    onAction: (CalorieIntakeAction) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.sheetContent != null) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = CalorieTrackerTheme.colors.surfacePrimary,
            contentColor = CalorieTrackerTheme.colors.onSurfacePrimary,
            onDismissRequest = { onAction(CalorieIntakeAction.DismissBottomSheet) }
        ) {
            when (state.sheetContent) {
                CalorieIntakeScreenSheetContent.CALORIES -> {
                    NutrientBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        contentTitle = stringResource(R.string.daily_calorie_intake),
                        nutrientName = stringResource(id = R.string.calories),
                        initialAmount = state.currentNutrients.caloriesGoal,
                        recommendedAmountText = stringResource(id = R.string.amount_cal, state.recommendedNutrients.caloriesGoal),
                        onConfirm = { calories -> onAction(CalorieIntakeAction.ChangeCalories(calories)) }
                    )
                }

                CalorieIntakeScreenSheetContent.PROTEINS -> {
                    NutrientBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        contentTitle = stringResource(R.string.daily_protein_intake),
                        nutrientName = stringResource(id = R.string.proteins),
                        initialAmount = state.currentNutrients.proteinsGoal,
                        recommendedAmountText = stringResource(id = R.string.amount_grams, state.recommendedNutrients.proteinsGoal),
                        onConfirm = { proteins -> onAction(CalorieIntakeAction.ChangeProteins(proteins)) }
                    )
                }

                CalorieIntakeScreenSheetContent.FATS -> {
                    NutrientBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        contentTitle = stringResource(R.string.daily_fat_intake),
                        nutrientName = stringResource(id = R.string.fats),
                        initialAmount = state.currentNutrients.fatsGoal,
                        recommendedAmountText = stringResource(
                            id = R.string.amount_grams,
                            state.recommendedNutrients.fatsGoal
                        ),
                        onConfirm = { fats -> onAction(CalorieIntakeAction.ChangeFats(fats)) }
                    )
                }

                CalorieIntakeScreenSheetContent.CARBS -> {
                    NutrientBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        contentTitle = stringResource(R.string.daily_carb_intake),
                        nutrientName = stringResource(id = R.string.carbs),
                        initialAmount = state.currentNutrients.carbsGoal,
                        recommendedAmountText = stringResource(
                            id = R.string.amount_grams,
                            state.recommendedNutrients.carbsGoal
                        ),
                        onConfirm = { carbs -> onAction(CalorieIntakeAction.ChangeCarbs(carbs)) }
                    )
                }

                CalorieIntakeScreenSheetContent.WATER -> {
                    NutrientBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        contentTitle = stringResource(R.string.daily_water_intake),
                        nutrientName = stringResource(id = R.string.water),
                        initialAmount = state.currentNutrients.waterMlGoal,
                        recommendedAmountText = stringResource(
                            id = R.string.water_milliliters,
                            state.recommendedNutrients.waterMlGoal
                        ),
                        onConfirm = { waterMl -> onAction(CalorieIntakeAction.ChangeWater(waterMl)) }
                    )
                }
            }
        }
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBackClick = {
                    onAction(CalorieIntakeAction.NavigateBackClick)
                }
            )
        },
        bottomBar = {
            SaveButtonSection(
                onSaveClick = { onAction(CalorieIntakeAction.SaveClick) },
                isLoading = state.isLoading
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.calories),
                    fieldValue = stringResource(
                        R.string.amount_cal,
                        state.currentNutrients.caloriesGoal
                    ),
                    onClick = { onAction(CalorieIntakeAction.CaloriesFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.proteins),
                    fieldValue = stringResource(
                        R.string.amount_grams,
                        state.currentNutrients.proteinsGoal
                    ),
                    onClick = { onAction(CalorieIntakeAction.ProteinsFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.fats),
                    fieldValue = stringResource(
                        R.string.amount_grams,
                        state.currentNutrients.fatsGoal
                    ),
                    onClick = { onAction(CalorieIntakeAction.FatsFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.carbs),
                    fieldValue = stringResource(
                        R.string.amount_grams,
                        state.currentNutrients.carbsGoal
                    ),
                    onClick = { onAction(CalorieIntakeAction.CarbsFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.water),
                    fieldValue = stringResource(
                        R.string.water_milliliters,
                        state.currentNutrients.waterMlGoal
                    ),
                    onClick = { onAction(CalorieIntakeAction.WaterFieldClick) }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun NutrientBottomSheetContent(
    modifier: Modifier,
    contentTitle: String,
    nutrientName: String,
    initialAmount: Int,
    recommendedAmountText: String,
    onConfirm: (nutrientAmount: Int) -> Unit,
) {
    var nutrientAmount by remember { mutableIntStateOf(initialAmount) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = contentTitle,
            style = CalorieTrackerTheme.typography.titleMedium,
            color = CalorieTrackerTheme.colors.onSurfacePrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        TextField(
            modifier = Modifier.fillMaxWidth(0.5f),
            value = nutrientAmount.toString(),
            onValueChange = { newValue ->
                 nutrientAmount = newValue.toIntOrNull() ?: 0
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
            label = {
                Text(
                    text = nutrientName,
                    color = CalorieTrackerTheme.colors.onSurfacePrimary,
                    style = CalorieTrackerTheme.typography.bodySmall
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
        Text(
            text = stringResource(R.string.recommended_amount, recommendedAmountText),
            color = CalorieTrackerTheme.colors.primary,
            style = CalorieTrackerTheme.typography.labelLarge
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
            onClick = { onConfirm(nutrientAmount) }
        ) {
            Text(
                text = stringResource(id = R.string.done),
                style = CalorieTrackerTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SaveButtonSection(
    onSaveClick: () -> Unit,
    isLoading: Boolean,
) {
    Button(
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
        contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
        enabled = !isLoading,
        onClick = onSaveClick,
    ) {
        Text(
            text = stringResource(R.string.save),
            style = CalorieTrackerTheme.typography.titleSmall,
            color = CalorieTrackerTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSection(
    onNavigateBackClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = CalorieTrackerTheme.padding.small),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = CalorieTrackerTheme.colors.background,
            titleContentColor = CalorieTrackerTheme.colors.onBackground,
            navigationIconContentColor = CalorieTrackerTheme.colors.onBackground
        ),
        title = {
            Text(
                text = stringResource(R.string.calorie_intake),
                style = CalorieTrackerTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = CalorieTrackerTheme.colors.onBackground
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onNavigateBackClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_back),
                    contentDescription = stringResource(R.string.desc_icon_navigate_back),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}
