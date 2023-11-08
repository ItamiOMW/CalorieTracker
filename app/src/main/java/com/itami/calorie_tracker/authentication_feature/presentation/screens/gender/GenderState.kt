package com.itami.calorie_tracker.authentication_feature.presentation.screens.gender

import com.itami.calorie_tracker.core.domain.model.Gender

data class GenderState(
    val isLoading: Boolean = false,
    val selectedGender: Gender = Gender.MALE,
)
