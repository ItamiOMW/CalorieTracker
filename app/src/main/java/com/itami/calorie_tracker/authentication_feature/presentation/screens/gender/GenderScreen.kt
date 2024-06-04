package com.itami.calorie_tracker.authentication_feature.presentation.screens.gender

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun GenderScreen(
    onGenderSaved: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: GenderViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            GenderUiEvent.GenderSaved -> onGenderSaved()
            GenderUiEvent.NavigateBack -> onNavigateBack()
        }
    }

    GenderScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun GenderScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        GenderScreenContent(
            state = GenderState(),
            onAction = {}
        )
    }
}

@Composable
private fun GenderScreenContent(
    state: GenderState,
    onAction: (GenderAction) -> Unit,
) {
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
            GendersSection(
                modifier = Modifier.fillMaxWidth(),
                selectedGender = state.selectedGender,
                onGenderClick = { gender ->
                    onAction(GenderAction.GenderClick(gender = gender))
                }
            )
            BottomSection(
                onNavigateNextClick = {
                    onAction(GenderAction.NavigateNextClick)
                },
                onNavigateBackClick = {
                    onAction(GenderAction.NavigateBackClick)
                },
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
            text = stringResource(R.string.gender_screen_title),
            color = CalorieTrackerTheme.colors.onBackground,
            style = CalorieTrackerTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
        Text(
            text = stringResource(R.string.gender_screen_description),
            color = CalorieTrackerTheme.colors.onBackgroundVariant,
            style = CalorieTrackerTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GendersSection(
    modifier: Modifier,
    selectedGender: Gender,
    onGenderClick: (gender: Gender) -> Unit,
) {
    val genders = remember {
        listOf(
            Gender.MALE,
            Gender.FEMALE,
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
    ) {
        genders.forEach { gender ->
            GenderItem(
                selected = gender == selectedGender,
                gender = gender,
                onClick = {
                    onGenderClick(gender)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CalorieTrackerTheme.padding.default)
            )
        }
    }
}

@Composable
private fun BottomSection(
    onNavigateNextClick: () -> Unit,
    onNavigateBackClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            modifier = Modifier.align(Alignment.Bottom),
            onClick = onNavigateBackClick
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
                    onNavigateNextClick()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenderItem(
    selected: Boolean,
    gender: Gender,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        shape = CalorieTrackerTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = CalorieTrackerTheme.colors.onBackground,
        ),
        border = if (selected) BorderStroke(1.dp, CalorieTrackerTheme.colors.primary) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = onClick,
    ) {
        Text(
            text = stringResource(
                id = when (gender) {
                    Gender.MALE -> {
                        R.string.gender_male
                    }

                    Gender.FEMALE -> {
                        R.string.gender_female
                    }
                }
            ),
            style = CalorieTrackerTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = CalorieTrackerTheme.padding.default),
        )
    }
}