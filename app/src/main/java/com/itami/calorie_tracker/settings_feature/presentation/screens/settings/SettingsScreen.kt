package com.itami.calorie_tracker.settings_feature.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.components.HeightUnitDialog
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OptionItem
import com.itami.calorie_tracker.core.presentation.components.ThemeDialog
import com.itami.calorie_tracker.core.presentation.components.WeightUnitDialog
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme.isDarkTheme
import com.itami.calorie_tracker.core.utils.capitalizeFirstLetter
import com.itami.calorie_tracker.core.presentation.components.WaterServingSizeDialog
import com.itami.calorie_tracker.core.presentation.components.WaterTrackerEnabledDialog
import java.util.Locale

@Composable
fun SettingsScreen(
    onNavigateToAccount: () -> Unit,
    onNavigateToLanguage: () -> Unit,
    onNavigateToSubscription: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            SettingsUiEvent.NavigateBack -> onNavigateBack()
            SettingsUiEvent.NavigateToAccount -> onNavigateToAccount()
            SettingsUiEvent.NavigateToLanguage -> onNavigateToLanguage()
            SettingsUiEvent.NavigateToSubscription -> onNavigateToSubscription()
        }
    }

    SettingsScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun SettingsScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.DARK_THEME) {
        SettingsScreenContent(
            state = SettingsState(),
            onAction = {}
        )
    }
}

@Composable
fun SettingsScreenContent(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    if (state.dialogContent != null) {
        when (state.dialogContent) {
            SettingsScreenDialogContent.WEIGHT_UNIT -> {
                WeightUnitDialog(
                    selectedWeightUnit = state.weightUnit,
                    onConfirm = { unit -> onAction(SettingsAction.ChangeWeightUnit(unit)) },
                    onDismiss = { onAction(SettingsAction.DismissDialog) }
                )
            }

            SettingsScreenDialogContent.HEIGHT_UNIT -> {
                HeightUnitDialog(
                    selectedHeightUnit = state.heightUnit,
                    onConfirm = { unit -> onAction(SettingsAction.ChangeHeightUnit(unit)) },
                    onDismiss = { onAction(SettingsAction.DismissDialog) }
                )
            }

            SettingsScreenDialogContent.WATER_SERVING_SIZE -> {
                WaterServingSizeDialog(
                    initialSizeMl = state.waterServingSizeMl,
                    onConfirm = { sizeMl -> onAction(SettingsAction.ChangeWaterServingSize(sizeMl)) },
                    onDismiss = { onAction(SettingsAction.DismissDialog) }
                )
            }

            SettingsScreenDialogContent.THEME -> {
                ThemeDialog(
                    initialTheme = state.theme,
                    onConfirm = { theme -> onAction(SettingsAction.ChangeTheme(theme)) },
                    onDismiss = { onAction(SettingsAction.DismissDialog) }
                )
            }

            SettingsScreenDialogContent.WATER_TRACKER -> {
                WaterTrackerEnabledDialog(
                    initialEnabled = state.waterIntakeEnabled,
                    onConfirm = { enabled -> onAction(SettingsAction.ChangeWaterTrackerEnabledState(enabled)) },
                    onDismiss = { onAction(SettingsAction.DismissDialog) }
                )
            }
        }
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(onNavigateBackClick = { onAction(SettingsAction.NavigateBackClick) })
        },
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
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.default)
            ) {
                AccountFieldSection(
                    user = state.user,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(SettingsAction.AccountFieldClick) }
                        .padding(
                            horizontal = CalorieTrackerTheme.padding.default,
                            vertical = CalorieTrackerTheme.padding.default
                        ),
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.small)
                        .fillMaxWidth(),
                    color = CalorieTrackerTheme.colors.outlineVariant
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(id = R.string.weight_unit),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_scale),
                                contentDescription = stringResource(R.string.desc_scale_icon),
                                tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = when (state.weightUnit) {
                                    WeightUnit.POUND -> stringResource(id = R.string.weight_unit_pounds)
                                    WeightUnit.KILOGRAM -> stringResource(id = R.string.weight_unit_kilograms)
                                },
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(SettingsAction.WeightUnitFieldClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(id = R.string.height_unit),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_ruler),
                                contentDescription = stringResource(R.string.desc_ruler_icon),
                                tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = when (state.heightUnit) {
                                    HeightUnit.FEET -> stringResource(id = R.string.height_unit_feet)
                                    HeightUnit.METER -> stringResource(id = R.string.height_unit_meter)
                                },
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(SettingsAction.HeightUnitFieldClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.water_serving_size),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_water),
                                contentDescription = stringResource(R.string.desc_water_icon),
                                tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = stringResource(
                                    id = R.string.water_milliliters,
                                    state.waterServingSizeMl
                                ),
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(SettingsAction.WaterServingSizeFieldClick) }
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.small)
                        .fillMaxWidth(),
                    color = CalorieTrackerTheme.colors.outlineVariant
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.language),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_language),
                                contentDescription = stringResource(R.string.desc_language_icon),
                                tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = Locale.getDefault().displayName.capitalizeFirstLetter(),
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(SettingsAction.LanguageFieldClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.theme),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_contrast),
                                contentDescription = stringResource(R.string.desc_icon_contrast),
                                tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = when (state.theme) {
                                    Theme.DARK_THEME -> stringResource(id = R.string.theme_dark)
                                    Theme.LIGHT_THEME -> stringResource(id = R.string.theme_light)
                                    Theme.SYSTEM_THEME -> stringResource(id = R.string.theme_system)
                                },
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(SettingsAction.ThemeFieldClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.water_tracker),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_humidity),
                                contentDescription = stringResource(R.string.desc_water_icon),
                                tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = if (state.waterIntakeEnabled) stringResource(R.string.enabled)
                                else stringResource(R.string.disabled),
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(SettingsAction.SubscriptionFieldClick) }
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.small)
                        .fillMaxWidth(),
                    color = CalorieTrackerTheme.colors.outlineVariant
                )
                OptionItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    optionText = stringResource(R.string.subscription),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_pay),
                            contentDescription = stringResource(R.string.desc_pay_icon),
                            tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                            else CalorieTrackerTheme.colors.primary,
                            modifier = Modifier.size(24.dp),
                        )
                    },
                    trailingContent = {
                        Text(
                            text = if (state.subscriptionEnabled) stringResource(R.string.enabled)
                            else stringResource(R.string.disabled),
                            color = CalorieTrackerTheme.colors.primary,
                            style = CalorieTrackerTheme.typography.bodyMedium
                        )
                    },
                    onClick = { onAction(SettingsAction.SubscriptionFieldClick) }
                )
            }
            Text(
                text = stringResource(R.string.current_app_version),
                textAlign = TextAlign.Center,
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = CalorieTrackerTheme.padding.medium),
            )
        }
    }
}

@Composable
private fun AccountFieldSection(
    modifier: Modifier = Modifier,
    user: User,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.profilePictureUrl,
            contentDescription = stringResource(R.string.desc_user_profile_picture),
            error = painterResource(id = R.drawable.unknown_person),
            placeholder = painterResource(id = R.drawable.unknown_person),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
        ) {
            Text(
                text = user.name,
                style = CalorieTrackerTheme.typography.titleMedium,
                color = CalorieTrackerTheme.colors.onBackground,
            )
            Text(
                text = user.email,
                style = CalorieTrackerTheme.typography.bodyMedium,
                color = if (isDarkTheme) CalorieTrackerTheme.colors.secondary
                else CalorieTrackerTheme.colors.primary,
            )
        }
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
                text = stringResource(R.string.settings),
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