package com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password

sealed class ResetPasswordAction {

    data class CodeInputChange(val newValue: String): ResetPasswordAction()

    data class PasswordInputChange(val newValue: String): ResetPasswordAction()

    data class PasswordVisibilityChange(val isVisible: Boolean): ResetPasswordAction()

    data class RepeatPasswordInputChange(val newValue: String): ResetPasswordAction()

    data class RepeatPasswordVisibilityChange(val isVisible: Boolean): ResetPasswordAction()

    data object ResendResetCode : ResetPasswordAction()

    data object ResetPassword : ResetPasswordAction()

}