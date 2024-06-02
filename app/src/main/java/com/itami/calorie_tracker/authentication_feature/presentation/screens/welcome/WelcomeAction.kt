package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

sealed class WelcomeAction {

    data class GoogleIdTokenReceived(val idToken: String) : WelcomeAction()

    data object DismissGoogleOneTap : WelcomeAction()

    data object OnSignInWithEmailClick : WelcomeAction()

    data object OnSignInWithGoogleClick : WelcomeAction()

    data object OnStartClick : WelcomeAction()

}
