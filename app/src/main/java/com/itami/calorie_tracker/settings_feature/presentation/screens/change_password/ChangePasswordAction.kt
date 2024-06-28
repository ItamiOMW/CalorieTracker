package com.itami.calorie_tracker.settings_feature.presentation.screens.change_password

sealed class ChangePasswordAction {

    data class OldPasswordChange(val newValue: String) : ChangePasswordAction()

    data class NewPasswordChange(val newValue: String) : ChangePasswordAction()

    data class RepeatNewPasswordChange(val newValue: String) : ChangePasswordAction()

    data object ChangeOldPasswordVisibility : ChangePasswordAction()

    data object ChangeNewPasswordVisibility : ChangePasswordAction()

    data object ChangeRepeatNewPasswordVisibility : ChangePasswordAction()

    data object ChangeClick : ChangePasswordAction()

    data object NavigateBackClick : ChangePasswordAction()

}