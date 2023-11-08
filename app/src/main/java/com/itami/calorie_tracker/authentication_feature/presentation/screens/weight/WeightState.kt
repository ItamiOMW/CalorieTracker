package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

import com.itami.calorie_tracker.core.domain.model.WeightUnit

data class WeightState(
    val isLoading: Boolean = false,
    val weight: String = "",
    val selectedWeightUnit: WeightUnit = WeightUnit.KILOGRAM,
)
