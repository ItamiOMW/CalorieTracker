package com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation

sealed class EmailActivationUiEvent {

    data class ShowSnackbar(val message: String): EmailActivationUiEvent()

}