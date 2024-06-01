package com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password

import com.itami.calorie_tracker.core.presentation.state.PasswordTextFieldState
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState

data class ResetPasswordState(
    val email: String = "",
    val codeState: StandardTextFieldState = StandardTextFieldState(),
    val passwordState: PasswordTextFieldState = PasswordTextFieldState(),
    val repeatPasswordState: PasswordTextFieldState = PasswordTextFieldState(),
    val isLoading: Boolean = false,
)
