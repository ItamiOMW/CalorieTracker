package com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password

sealed class ResetPasswordUiEvent {

    data class ShowSnackbar(val message: String): ResetPasswordUiEvent()

    data object PasswordResetSuccessful : ResetPasswordUiEvent()

    data object NavigateBack : ResetPasswordUiEvent()

}