package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal

data class RecommendedNutrientsState(
    val dailyNutrientsGoal: DailyNutrientsGoal? = null,
    val showGoogleOneTap: Boolean = false,
    val isLoading: Boolean = false,
)
