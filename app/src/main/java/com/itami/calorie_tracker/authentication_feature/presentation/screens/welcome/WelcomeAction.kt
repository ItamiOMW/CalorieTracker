package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

sealed class WelcomeAction {

    data class GoogleIdTokenReceived(val idToken: String) : WelcomeAction()

    data class GoogleIdTokenNoteReceived(val cause: String?) : WelcomeAction()

    data object SignInWithEmailClick : WelcomeAction()

    data object SignInWithGoogleClick : WelcomeAction()

    data object StartClick : WelcomeAction()

}
