package com.itami.calorie_tracker.settings_feature.presentation.screens.account

sealed class AccountUiEvent {

    data object LogoutSuccessful : AccountUiEvent()

    data object DeleteAccountSuccessful : AccountUiEvent()

    data object NavigateToChangePassword : AccountUiEvent()

    data object NavigateBack : AccountUiEvent()

    data class ShowSnackbar(val message: String) : AccountUiEvent()

}