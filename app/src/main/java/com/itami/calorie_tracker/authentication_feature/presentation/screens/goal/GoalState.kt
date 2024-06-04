package com.itami.calorie_tracker.authentication_feature.presentation.screens.goal

import com.itami.calorie_tracker.core.domain.model.WeightGoal

data class GoalState(
    val selectedGoal: WeightGoal = WeightGoal.GAIN_WEIGHT,
    val isLoading: Boolean = false,
)
