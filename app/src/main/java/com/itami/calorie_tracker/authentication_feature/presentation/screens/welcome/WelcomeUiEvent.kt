package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

sealed class WelcomeUiEvent {

    data object SignInSuccessful: WelcomeUiEvent()

    data class ShowSnackbar(val message: String): WelcomeUiEvent()

}
