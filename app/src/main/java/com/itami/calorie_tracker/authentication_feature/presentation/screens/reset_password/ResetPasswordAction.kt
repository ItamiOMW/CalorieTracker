package com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password

sealed class ResetPasswordAction {

    data class CodeInputChange(val newValue: String): ResetPasswordAction()

    data class PasswordInputChange(val newValue: String): ResetPasswordAction()

    data object PasswordVisibilityIconClick : ResetPasswordAction()

    data class RepeatPasswordInputChange(val newValue: String): ResetPasswordAction()

    data object RepeatPasswordVisibilityIconClick : ResetPasswordAction()

    data object ResendResetCodeClick : ResetPasswordAction()

    data object ResetPasswordClick : ResetPasswordAction()

    data object NavigateBackClick : ResetPasswordAction()

}