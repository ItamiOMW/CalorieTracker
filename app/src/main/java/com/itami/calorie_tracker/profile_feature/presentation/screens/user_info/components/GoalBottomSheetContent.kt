package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun GoalBottomSheetContent(
    modifier: Modifier = Modifier,
    initialGoal: WeightGoal = WeightGoal.KEEP_WEIGHT,
    onConfirm: (weightGoal: WeightGoal) -> Unit,
) {
    var weightGoalState by remember { mutableStateOf(initialGoal) }

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
    ) {
        Text(
            text = stringResource(id = R.string.goal),
            style = CalorieTrackerTheme.typography.titleMedium,
            color = CalorieTrackerTheme.colors.onSurfacePrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
        ) {
            weightGoals.forEach { weightGoal ->
                WeightGoalItem(
                    selected = weightGoal == weightGoalState,
                    weightGoal = weightGoal,
                    onClick = { weightGoalState = weightGoal },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CalorieTrackerTheme.padding.default)
                )
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = CalorieTrackerTheme.colors.primary,
                contentColor = CalorieTrackerTheme.colors.onPrimary,
            ),
            contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            onClick = { onConfirm(weightGoalState) }
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
            style = CalorieTrackerTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = CalorieTrackerTheme.padding.default),
        )
    }
}