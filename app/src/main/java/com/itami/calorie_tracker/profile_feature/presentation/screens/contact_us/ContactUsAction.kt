package com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us

sealed class ContactUsAction {

    data object NavigateBackClick : ContactUsAction()

    data object SendMessageClick : ContactUsAction()

    data class MessageValueChange(val newValue: String) : ContactUsAction()

    data object EmailLinkClick : ContactUsAction()

    data object TelegramLinkClick : ContactUsAction()

    data object GithubLinkClick : ContactUsAction()

}