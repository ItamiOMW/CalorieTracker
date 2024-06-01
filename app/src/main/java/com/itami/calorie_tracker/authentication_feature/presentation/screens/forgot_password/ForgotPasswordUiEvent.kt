package com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password

sealed class ForgotPasswordUiEvent {

    data class ShowSnackbar(val message: String) : ForgotPasswordUiEvent()

    data class ResetCodeSentSuccessfully(val email: String) : ForgotPasswordUiEvent()

}