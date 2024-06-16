package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.components.DialogComponent
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OptionItem
import com.itami.calorie_tracker.core.presentation.components.WeightUnitDialog
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ProfileScreen(
    onLogoutSuccess: () -> Unit,
    onNavigateToUserInfo: () -> Unit,
    onNavigateToCalorieIntake: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToContactUs: () -> Unit,
    onNavigateToAboutApp: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            ProfileUiEvent.NavigateBack -> onNavigateBack()
            ProfileUiEvent.NavigateToAbout -> onNavigateToAboutApp()
            ProfileUiEvent.NavigateToCalorieIntake -> onNavigateToCalorieIntake()
            ProfileUiEvent.NavigateToContactsUs -> onNavigateToContactUs()
            ProfileUiEvent.NavigateToMyInfo -> onNavigateToUserInfo()
            ProfileUiEvent.NavigateToSettings -> onNavigateToSettings()
            ProfileUiEvent.LogoutSuccess -> onLogoutSuccess()
        }
    }

    ProfileScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun ProfileScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ProfileScreenContent(
            state = ProfileState(showLogoutDialog = true),
            onAction = {}
        )
    }
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    if (state.showLogoutDialog) {
        DialogComponent(
            title = stringResource(id = R.string.logout),
            description = stringResource(R.string.you_sure_you_want_to_log_out),
            cancelText = stringResource(id = R.string.cancel),
            confirmText = stringResource(R.string.log_out),
            onConfirm = { onAction(ProfileAction.ConfirmLogout) },
            onDismiss = { onAction(ProfileAction.DenyLogout) },
            confirmTextColor = CalorieTrackerTheme.colors.danger
        )
    }

    if (state.showWeightUnitDialog) {
        WeightUnitDialog(
            selectedWeightUnit = state.weightUnit,
            onConfirm = { weightUnit ->
                onAction(ProfileAction.SaveWeightUnit(weightUnit))
            },
            onDismiss = {
                onAction(ProfileAction.DismissWeightUnitDialog)
            }
        )
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBackClick = {
                    onAction(ProfileAction.NavigateBackClick)
                }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier.padding(scaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                ProfileInfoSection(
                    user = state.user,
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.large)
                        .wrapContentWidth()
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                MainNavigationButtons(
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.default)
                        .fillMaxWidth(),
                    user = state.user,
                    selectedWeightUnit = state.weightUnit,
                    onMeClick = {
                        onAction(ProfileAction.MeClick)
                    },
                    onCalorieIntakeClick = {
                        onAction(ProfileAction.CalorieIntakeClick)
                    },
                    onWeightUnitClick = {
                        onAction(ProfileAction.WeightUnitClick)
                    }
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.small)
                        .fillMaxWidth(),
                    color = CalorieTrackerTheme.colors.outlineVariant
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
                OptionsSection(
                    modifier = Modifier.fillMaxWidth(),
                    onContactUsClick = {
                        onAction(ProfileAction.ContactUsClick)
                    },
                    onAboutAppClick = {
                        onAction(ProfileAction.AboutAppClick)
                    },
                    onSettingsClick = {
                        onAction(ProfileAction.SettingsClick)
                    },
                    onLogoutClick = {
                        if (!state.isLoggingOut) {
                            onAction(ProfileAction.LogoutClick)
                        }
                    },
                    onChangeTheme = { theme ->
                        onAction(ProfileAction.ChangeTheme(theme))
                    },
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
private fun OptionsSection(
    modifier: Modifier,
    onChangeTheme: (theme: Theme) -> Unit,
    onContactUsClick: () -> Unit,
    onAboutAppClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val isDarkTheme = CalorieTrackerTheme.isDarkTheme
        OptionItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            optionText = stringResource(R.string.dark_theme),
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
                Switch(
                    modifier = Modifier.scale(0.7f),
                    checked = isDarkTheme,
                    onCheckedChange = { checked ->
                        onChangeTheme(if (checked) Theme.DARK_THEME else Theme.LIGHT_THEME)
                    },
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = CalorieTrackerTheme.colors.primary,
                        checkedTrackColor = CalorieTrackerTheme.colors.primary,
                        uncheckedTrackColor = Color.Transparent,
                        checkedIconColor = CalorieTrackerTheme.colors.onPrimary,
                        uncheckedIconColor = CalorieTrackerTheme.colors.primary,
                        checkedThumbColor = CalorieTrackerTheme.colors.onPrimary,
                        uncheckedThumbColor = CalorieTrackerTheme.colors.primary,
                    ),
                )
            },
            onClick = {
                onChangeTheme(if (isDarkTheme) Theme.LIGHT_THEME else Theme.DARK_THEME)
            }
        )
        OptionItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            optionText = stringResource(R.string.title_contact_us),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_mail),
                    contentDescription = stringResource(R.string.desc_icon_mail),
                    tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                    else CalorieTrackerTheme.colors.primary,
                    modifier = Modifier.size(24.dp),
                )
            },
            onClick = onContactUsClick
        )
        OptionItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            optionText = stringResource(R.string.title_about_app),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_info),
                    contentDescription = stringResource(R.string.desc_icon_info),
                    tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                    else CalorieTrackerTheme.colors.primary,
                    modifier = Modifier.size(24.dp),
                )
            },
            onClick = onAboutAppClick
        )
        OptionItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            optionText = stringResource(R.string.settings),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_settings),
                    contentDescription = stringResource(R.string.desc_icon_settings),
                    tint = if (isDarkTheme) CalorieTrackerTheme.colors.onBackground
                    else CalorieTrackerTheme.colors.primary,
                    modifier = Modifier.size(24.dp),
                )
            },
            onClick = onSettingsClick
        )
        OptionItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            optionText = stringResource(R.string.logout),
            textColor = CalorieTrackerTheme.colors.danger,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_logout),
                    contentDescription = stringResource(R.string.desc_icon_settings),
                    tint = CalorieTrackerTheme.colors.danger,
                    modifier = Modifier.size(24.dp),
                )
            },
            onClick = onLogoutClick
        )
    }
}


@Composable
private fun MainNavigationButtons(
    modifier: Modifier,
    user: User,
    selectedWeightUnit: WeightUnit,
    onMeClick: () -> Unit,
    onCalorieIntakeClick: () -> Unit,
    onWeightUnitClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
    ) {
        Button(
            modifier = Modifier.height(50.dp),
            onClick = onMeClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.surfacePrimary
                else CalorieTrackerTheme.colors.primary,
                contentColor = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.primary
                else CalorieTrackerTheme.colors.onPrimary
            ),
            contentPadding = PaddingValues(horizontal = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.me),
                    style = CalorieTrackerTheme.typography.labelLarge,
                )
                Icon(
                    painter = painterResource(id = R.drawable.icon_navigate_next),
                    contentDescription = stringResource(id = R.string.desc_icon_navigate_next),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Button(
            modifier = Modifier.height(50.dp),
            onClick = onCalorieIntakeClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.surfacePrimary
                else CalorieTrackerTheme.colors.surfaceSecondary,
            ),
            contentPadding = PaddingValues(horizontal = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.calorie_intake),
                    style = CalorieTrackerTheme.typography.bodyMedium,
                    color = CalorieTrackerTheme.colors.onSurfacePrimary
                )
                Text(
                    text = stringResource(
                        R.string.cal_amount,
                        user.dailyNutrientsGoal.caloriesGoal
                    ),
                    style = CalorieTrackerTheme.typography.labelLarge,
                    color = CalorieTrackerTheme.colors.primary
                )
            }
        }
        Button(
            modifier = Modifier.height(50.dp),
            onClick = onWeightUnitClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.surfacePrimary
                else CalorieTrackerTheme.colors.surfaceSecondary,
            ),
            contentPadding = PaddingValues(horizontal = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.weight_unit),
                    style = CalorieTrackerTheme.typography.bodyMedium,
                    color = CalorieTrackerTheme.colors.onSurfacePrimary
                )
                Text(
                    text = stringResource(
                        id = when (selectedWeightUnit) {
                            WeightUnit.POUND -> R.string.weight_unit_pounds
                            WeightUnit.KILOGRAM -> R.string.weight_unit_kilograms
                        },
                    ),
                    style = CalorieTrackerTheme.typography.labelLarge,
                    color = CalorieTrackerTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoSection(
    modifier: Modifier,
    user: User,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = user.profilePictureUrl,
            contentDescription = stringResource(R.string.desc_user_profile_picture),
            error = painterResource(id = R.drawable.unknown_person),
            placeholder = painterResource(id = R.drawable.unknown_person),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
        Text(
            text = user.name,
            style = CalorieTrackerTheme.typography.titleLarge,
            color = CalorieTrackerTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.tiny))
        Text(
            text = user.email,
            style = CalorieTrackerTheme.typography.titleSmall,
            color = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.secondary
            else CalorieTrackerTheme.colors.primary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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
                text = stringResource(R.string.title_profile),
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