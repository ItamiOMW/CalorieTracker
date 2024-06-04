package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OptionItem
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ProfileScreen(
    onNavigateToMyInfo: () -> Unit,
    onNavigateToCalorieIntake: () -> Unit,
    onNavigateToWaterIntake: () -> Unit,
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
            ProfileUiEvent.NavigateToMyInfo -> onNavigateToMyInfo()
            ProfileUiEvent.NavigateToSettings -> onNavigateToSettings()
            ProfileUiEvent.NavigateToWaterIntake -> onNavigateToWaterIntake()
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
            state = ProfileState(),
            onAction = {}
        )
    }
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
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
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
            ProfileInfoSection(
                modifier = Modifier
                    .padding(horizontal = CalorieTrackerTheme.padding.large)
                    .wrapContentWidth(),
                user = state.user
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
            MainNavigationButtons(
                modifier = Modifier
                    .padding(horizontal = CalorieTrackerTheme.padding.default)
                    .fillMaxWidth(),
                user = state.user,
                onMyInfoClick = {
                    onAction(ProfileAction.MyInfoClick)
                },
                onCalorieIntakeClick = {
                    onAction(ProfileAction.CalorieIntakeClick)
                },
                onWaterIntakeClick = {
                    onAction(ProfileAction.WaterIntakeClick)
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
                    onAction(ProfileAction.AboutClick)
                },
                onSettingsClick = {
                    onAction(ProfileAction.SettingsClick)
                },
                onChangeTheme = { theme ->
                    onAction(ProfileAction.ChangeTheme(theme))
                }
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
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val isDarkTheme = CalorieTrackerTheme.isDarkTheme
        OptionItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
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
            optionText = stringResource(R.string.contact_us),
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
            optionText = stringResource(R.string.about_app),
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
    }
}


@Composable
private fun MainNavigationButtons(
    modifier: Modifier,
    user: User,
    onMyInfoClick: () -> Unit,
    onCalorieIntakeClick: () -> Unit,
    onWaterIntakeClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
    ) {
        Button(
            modifier = Modifier.height(50.dp),
            onClick = onMyInfoClick,
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
                    text = stringResource(R.string.my_info),
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
            onClick = onWaterIntakeClick,
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
                    text = stringResource(R.string.water_intake),
                    style = CalorieTrackerTheme.typography.bodyMedium,
                    color = CalorieTrackerTheme.colors.onSurfacePrimary
                )
                Text(
                    text = stringResource(
                        R.string.water_milliliters,
                        user.dailyNutrientsGoal.waterMlGoal
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

@Composable
private fun TopBarSection(
    onNavigateBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(CalorieTrackerTheme.padding.default)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.weight(weight = 0.3f, fill = true),
            onClick = onNavigateBackClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = stringResource(R.string.desc_icon_navigate_back),
                tint = CalorieTrackerTheme.colors.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            modifier = Modifier.weight(weight = 1f, fill = true),
            text = stringResource(R.string.profile),
            style = CalorieTrackerTheme.typography.titleMedium,
            color = CalorieTrackerTheme.colors.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(
            Modifier
                .size(24.dp)
                .weight(weight = 0.3f, fill = true)
        )
    }
}