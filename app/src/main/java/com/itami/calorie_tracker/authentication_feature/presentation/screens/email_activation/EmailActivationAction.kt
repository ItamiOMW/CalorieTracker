package com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation

sealed class EmailActivationAction {

    data object OnResendConfirmationEmail: EmailActivationAction()

}