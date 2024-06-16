package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit

data class ProfileState(
    val user: User = User.DEFAULT,
    val weightUnit: WeightUnit = WeightUnit.KILOGRAM,
    val showLogoutDialog: Boolean = false,
    val showWeightUnitDialog: Boolean = false,
    val isLoggingOut: Boolean = false,
)
