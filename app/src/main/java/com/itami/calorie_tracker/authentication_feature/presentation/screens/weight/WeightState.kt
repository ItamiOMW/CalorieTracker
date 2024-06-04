package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

import com.itami.calorie_tracker.core.domain.model.WeightUnit

data class WeightState(
    val weight: String = "",
    val selectedWeightUnit: WeightUnit = WeightUnit.KILOGRAM,
    val isLoading: Boolean = false,
)
