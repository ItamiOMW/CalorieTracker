package com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password

import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState

data class ForgotPasswordState(
    val emailState: StandardTextFieldState = StandardTextFieldState(),
    val isLoading: Boolean = false,
)
