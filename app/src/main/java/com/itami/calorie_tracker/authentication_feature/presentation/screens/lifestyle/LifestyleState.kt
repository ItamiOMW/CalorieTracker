package com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle

import com.itami.calorie_tracker.core.domain.model.Lifestyle

data class LifestyleState(
    val selectedLifestyle: Lifestyle = Lifestyle.LOW_ACTIVE,
    val isLoading: Boolean = false,
)
