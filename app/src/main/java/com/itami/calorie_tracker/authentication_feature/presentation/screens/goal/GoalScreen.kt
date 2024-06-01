package com.itami.calorie_tracker.authentication_feature.presentation.screens.goal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun GoalScreen(
    onNavigateToGender: () -> Unit,
    state: GoalState,
    uiEvent: Flow<GoalUiEvent>,
    onAction: (GoalAction) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                GoalUiEvent.GoalSaved -> {
                    onNavigateToGender()
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
            WeightGoalsSection(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedWeightGoal = state.selectedGoal,
                onWeightGoalClick = { goal ->
                    onAction(GoalAction.SelectGoal(weightGoal = goal))
                }
            )
            BottomSection(
                onFABClick = {
                    onAction(GoalAction.SaveGoal)
                },
                isLoading = state.isLoading,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
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
            text = stringResource(R.string.goal_screen_title),
            color = CalorieTrackerTheme.colors.onBackground,
            style = CalorieTrackerTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
        Text(
            text = stringResource(R.string.goal_screen_description),
            color = CalorieTrackerTheme.colors.onBackgroundVariant,
            style = CalorieTrackerTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WeightGoalsSection(
    modifier: Modifier,
    selectedWeightGoal:  WeightGoal,
    onWeightGoalClick: (weightGoal: WeightGoal) -> Unit,
) {
    val weightGoals = remember {
        listOf(
            WeightGoal.LOSE_WEIGHT,
            WeightGoal.KEEP_WEIGHT,
            WeightGoal.GAIN_WEIGHT,
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
    ) {
        weightGoals.forEach { weightGoal ->
            WeightGoalItem(
                selected = weightGoal == selectedWeightGoal,
                weightGoal = weightGoal,
                onClick = {
                    onWeightGoalClick(weightGoal)
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
    onFABClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
    ) {
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
                .clip(CircleShape)
                .align(Alignment.CenterEnd),
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
private fun WeightGoalItem(
    selected: Boolean,
    weightGoal: WeightGoal,
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
                id = when (weightGoal) {
                    WeightGoal.LOSE_WEIGHT -> {
                        R.string.goal_lose_weight
                    }

                    WeightGoal.KEEP_WEIGHT -> {
                        R.string.goal_keep_weight
                    }

                    WeightGoal.GAIN_WEIGHT -> {
                        R.string.goal_gain_weight
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