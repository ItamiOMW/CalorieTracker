package com.itami.calorie_tracker.settings_feature.presentation.screens.change_password

sealed class ChangePasswordUiEvent {

    data class ShowSnackbar(val message: String) : ChangePasswordUiEvent()

    data object NavigateBack : ChangePasswordUiEvent()

    data object PasswordChanged : ChangePasswordUiEvent()

}