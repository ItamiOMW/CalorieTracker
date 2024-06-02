package com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password

sealed class ForgotPasswordAction {

    data class EmailInputChange(val newValue: String): ForgotPasswordAction()

    data object SendResetCodeClick: ForgotPasswordAction()

    data object NavigateBackClick: ForgotPasswordAction()

}