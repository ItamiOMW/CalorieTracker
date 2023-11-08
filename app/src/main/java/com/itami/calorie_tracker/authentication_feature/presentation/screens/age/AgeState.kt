package com.itami.calorie_tracker.authentication_feature.presentation.screens.age

import com.itami.calorie_tracker.core.domain.model.WeightUnit

data class AgeState(
    val isLoading: Boolean = false,
    val age: String = "",
    val selectedWeightUnit: WeightUnit = WeightUnit.KILOGRAM,
)
