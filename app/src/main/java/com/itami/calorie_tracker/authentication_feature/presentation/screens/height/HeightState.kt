package com.itami.calorie_tracker.authentication_feature.presentation.screens.height

import com.itami.calorie_tracker.core.domain.model.HeightUnit

data class HeightState(
    val isLoading: Boolean = false,
    val heightCm: Int = 0,
    val selectedHeightUnit: HeightUnit = HeightUnit.METER,
)
