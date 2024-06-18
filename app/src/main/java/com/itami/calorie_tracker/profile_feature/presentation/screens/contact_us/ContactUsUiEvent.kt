package com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us

sealed class ContactUsUiEvent {

    data class ShowSnackbar(val message: String) : ContactUsUiEvent()

    data object NavigateBack : ContactUsUiEvent()

    data class NavigateToEmail(val email: String) : ContactUsUiEvent()

    data class NavigateToTelegram(val telegramUsername: String) : ContactUsUiEvent()

    data class NavigateToGithub(val githubUsername: String) : ContactUsUiEvent()

}