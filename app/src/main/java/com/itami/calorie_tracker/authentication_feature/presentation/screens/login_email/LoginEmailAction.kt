package com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email

sealed class LoginEmailAction {

    data class EmailInputChange(val newValue: String) : LoginEmailAction()

    data class PasswordInputChange(val newValue: String) : LoginEmailAction()

    data class PasswordVisibilityChange(val isVisible: Boolean) : LoginEmailAction()

    data object LoginClick : LoginEmailAction()

    data object ForgotPasswordClick : LoginEmailAction()

    data object NavigateBackClick : LoginEmailAction()

}