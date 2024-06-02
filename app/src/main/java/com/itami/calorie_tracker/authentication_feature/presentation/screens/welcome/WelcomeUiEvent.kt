package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

sealed class WelcomeUiEvent {

    data object GoogleLoginSuccessful: WelcomeUiEvent()

    data object Start: WelcomeUiEvent()

    data object NavigateToLoginEmail: WelcomeUiEvent()

    data class ShowSnackbar(val message: String): WelcomeUiEvent()

}
