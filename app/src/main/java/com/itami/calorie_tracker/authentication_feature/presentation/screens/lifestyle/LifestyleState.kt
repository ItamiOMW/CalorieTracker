package com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle

import com.itami.calorie_tracker.core.domain.model.Lifestyle

data class LifestyleState(
    val isLoading: Boolean = false,
    val selectedLifestyle: Lifestyle = Lifestyle.LOW_ACTIVE,
)
