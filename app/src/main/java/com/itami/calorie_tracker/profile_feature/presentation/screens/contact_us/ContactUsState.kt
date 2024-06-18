package com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us

import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState

data class ContactUsState(
    val messageState: StandardTextFieldState = StandardTextFieldState(),
    val isLoading: Boolean = false,
)
