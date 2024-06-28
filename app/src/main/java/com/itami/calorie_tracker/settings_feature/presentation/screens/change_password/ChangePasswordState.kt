package com.itami.calorie_tracker.settings_feature.presentation.screens.change_password

import com.itami.calorie_tracker.core.presentation.state.PasswordTextFieldState

data class ChangePasswordState(
    val oldPassword: PasswordTextFieldState = PasswordTextFieldState(),
    val newPassword: PasswordTextFieldState = PasswordTextFieldState(),
    val repeatNewPassword: PasswordTextFieldState = PasswordTextFieldState(),
    val isLoading: Boolean = false,
)
