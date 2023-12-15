package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import com.itami.calorie_tracker.core.domain.model.User

data class ProfileState(
    val user: User = User.DEFAULT,
)
