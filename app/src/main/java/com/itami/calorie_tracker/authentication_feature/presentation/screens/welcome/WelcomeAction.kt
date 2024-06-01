package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

sealed class WelcomeAction {

    data class SignInWithGoogle(val idToken: String): WelcomeAction()

    data class ShowGoogleOneTap(val show: Boolean): WelcomeAction()

}
