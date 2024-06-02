package com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email

sealed class LoginEmailUiEvent {

    data class ShowSnackbar(val message: String): LoginEmailUiEvent()

    data object LoginSuccessful: LoginEmailUiEvent()

    data object NavigateToForgotPassword: LoginEmailUiEvent()

    data object NavigateBack: LoginEmailUiEvent()

}