package com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email

import com.itami.calorie_tracker.core.presentation.state.PasswordTextFieldState
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState

data class LoginEmailState(
    val emailState: StandardTextFieldState = StandardTextFieldState(),
    val passwordState: PasswordTextFieldState = PasswordTextFieldState(),
    val isLoading: Boolean = false,
)
