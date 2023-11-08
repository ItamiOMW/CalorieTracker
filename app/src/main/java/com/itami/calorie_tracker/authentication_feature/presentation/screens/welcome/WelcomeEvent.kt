package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

sealed class WelcomeEvent {

    data class SignInWithGoogle(val idToken: String): WelcomeEvent()

    data class ShowGoogleOneTap(val show: Boolean): WelcomeEvent()

}
