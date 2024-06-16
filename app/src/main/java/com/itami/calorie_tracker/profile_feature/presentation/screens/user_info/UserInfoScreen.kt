package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.UnderlinedFieldWithValue
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components.AgeBottomSheetContent
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components.GenderBottomSheetContent
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components.GoalBottomSheetContent
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components.HeightBottomSheetContent
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components.LifestyleBottomSheetContent
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components.WeightBottomSheetContent

@Composable
fun UserInfoScreen(
    onNavigateBack: () -> Unit,
    onUserInfoSaved: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: UserInfoViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is UserInfoUiEvent.NavigateBack -> onNavigateBack()
            is UserInfoUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
            is UserInfoUiEvent.UserInfoSaved -> onUserInfoSaved()
        }
    }

    UserInfoScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun UserInfoScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        UserInfoScreenContent(
            state = UserInfoState(),
            onAction = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserInfoScreenContent(
    state: UserInfoState,
    onAction: (UserInfoAction) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.sheetContent != null) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = CalorieTrackerTheme.colors.surfacePrimary,
            contentColor = CalorieTrackerTheme.colors.onSurfacePrimary,
            onDismissRequest = { onAction(UserInfoAction.DismissBottomSheet) },
        ) {
            when (state.sheetContent) {
                UserInfoScreenSheetContent.GOAL -> {
                    GoalBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        initialGoal = state.user.weightGoal,
                        onConfirm = { goal -> onAction(UserInfoAction.ChangeGoal(goal)) }
                    )
                }

                UserInfoScreenSheetContent.AGE -> {
                    AgeBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        initialAge = state.user.age,
                        onConfirm = { age -> onAction(UserInfoAction.ChangeAge(age)) }
                    )
                }

                UserInfoScreenSheetContent.HEIGHT -> {
                    HeightBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        initialHeightCm = state.user.heightCm,
                        heightUnit = state.heightUnit,
                        onHeightUnitChange = { onAction(UserInfoAction.ChangeHeightUnit(it)) },
                        onConfirm = { heightCm -> onAction(UserInfoAction.ChangeHeight(heightCm)) }
                    )
                }

                UserInfoScreenSheetContent.WEIGHT -> {
                    WeightBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        initialWeightGrams = state.user.weightGrams,
                        weightUnit = state.weightUnit,
                        onWeightUnitChange = { unit -> onAction(UserInfoAction.ChangeWeightUnit(unit)) },
                        onConfirm = { weightGrams -> onAction(UserInfoAction.ChangeWeight(weightGrams)) }
                    )
                }

                UserInfoScreenSheetContent.GENDER -> {
                    GenderBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        initialGender = state.user.gender,
                        onConfirm = { gender -> onAction(UserInfoAction.ChangeGender(gender)) }
                    )
                }

                UserInfoScreenSheetContent.LIFESTYLE -> {
                    LifestyleBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = CalorieTrackerTheme.padding.default,
                                end = CalorieTrackerTheme.padding.default,
                                bottom = CalorieTrackerTheme.padding.large
                            ),
                        initialLifestyle = state.user.lifestyle,
                        onConfirm = { lifestyle -> onAction(UserInfoAction.ChangeLifestyle(lifestyle)) }
                    )
                }
            }
        }
    }

    if (state.showSaveDialog) {
        SaveDialog(
            onDismiss = { onAction(UserInfoAction.SaveUserDeny) },
            onConfirm = { updateNutrients -> onAction(UserInfoAction.SaveUserConfirm(updateNutrients)) }
        )
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(onNavigateBackClick = { onAction(UserInfoAction.NavigateBackClick) })
        },
        bottomBar = {
            SaveButtonSection(
                onSaveClick = { onAction(UserInfoAction.SaveClick) },
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
                    fieldName = stringResource(R.string.goal),
                    fieldValue = when (state.user.weightGoal) {
                        WeightGoal.LOSE_WEIGHT -> stringResource(id = R.string.goal_lose_weight)
                        WeightGoal.KEEP_WEIGHT -> stringResource(id = R.string.goal_keep_weight)
                        WeightGoal.GAIN_WEIGHT -> stringResource(id = R.string.goal_gain_weight)
                    },
                    onClick = { onAction(UserInfoAction.GoalFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.age),
                    fieldValue = stringResource(R.string.number_of_years, state.user.age),
                    onClick = { onAction(UserInfoAction.AgeFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.height),
                    fieldValue = state.heightUnit.format(state.user.heightCm),
                    onClick = { onAction(UserInfoAction.HeightFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.weight),
                    fieldValue = state.weightUnit.format(state.user.weightGrams),
                    onClick = { onAction(UserInfoAction.WeightFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.gender),
                    fieldValue = when (state.user.gender) {
                        Gender.MALE -> stringResource(id = R.string.gender_male)
                        Gender.FEMALE -> stringResource(id = R.string.gender_female)
                    },
                    onClick = { onAction(UserInfoAction.GenderFieldClick) }
                )
                UnderlinedFieldWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    fieldName = stringResource(R.string.lifestyle),
                    fieldValue = when (state.user.lifestyle) {
                        Lifestyle.SEDENTARY -> stringResource(id = R.string.lifestyle_sedentary)
                        Lifestyle.LOW_ACTIVE -> stringResource(id = R.string.lifestyle_low_active)
                        Lifestyle.ACTIVE -> stringResource(id = R.string.lifestyle_active)
                        Lifestyle.VERY_ACTIVE -> stringResource(id = R.string.lifestyle_very_active)
                    },
                    onClick = { onAction(UserInfoAction.LifestyleFieldClick) }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun SaveDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (updateNutrients: Boolean) -> Unit,
) {
    var updateNutrients by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = modifier,
            shape = CalorieTrackerTheme.shapes.small,
            color = CalorieTrackerTheme.colors.surfacePrimary,
            contentColor = CalorieTrackerTheme.colors.onSurfacePrimary,
            tonalElevation = 5.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = CalorieTrackerTheme.padding.small,
                        end = CalorieTrackerTheme.padding.small,
                        top = CalorieTrackerTheme.padding.small,
                        bottom = CalorieTrackerTheme.padding.extraSmall
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    style = CalorieTrackerTheme.typography.titleMedium,
                    color = CalorieTrackerTheme.colors.onSurfacePrimary,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            checkedColor = CalorieTrackerTheme.colors.primary,
                            uncheckedColor = CalorieTrackerTheme.colors.primary,
                            checkmarkColor = CalorieTrackerTheme.colors.onPrimary
                        ),
                        checked = updateNutrients,
                        onCheckedChange = { checked -> updateNutrients = checked }
                    )
                    Text(
                        text = stringResource(R.string.update_nutrients),
                        style = CalorieTrackerTheme.typography.labelLarge,
                        color = CalorieTrackerTheme.colors.onSurfacePrimary,
                    )
                }
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
                            text = stringResource(id = R.string.cancel),
                            style = CalorieTrackerTheme.typography.labelLarge,
                            color = CalorieTrackerTheme.colors.primary,
                            maxLines = 1
                        )
                    }
                    TextButton(
                        onClick = { onConfirm(updateNutrients) }
                    ) {
                        Text(
                            text = stringResource(id = R.string.OK),
                            style = CalorieTrackerTheme.typography.labelLarge,
                            color = CalorieTrackerTheme.colors.primary,
                            maxLines = 1
                        )
                    }
                }
            }
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
                text = stringResource(R.string.me),
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
