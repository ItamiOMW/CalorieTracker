package com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email

sealed class RegisterEmailUiEvent {

    data class ShowSnackbar(val message: String) : RegisterEmailUiEvent()

    data class RegisterSuccessful(val registeredEmail: String) : RegisterEmailUiEvent()

    data object NavigateBack : RegisterEmailUiEvent()

}