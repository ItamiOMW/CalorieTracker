package com.itami.calorie_tracker.profile_feature.presentation.screens.about_app

sealed class AboutAppUiEvent {

    data object NavigateBack: AboutAppUiEvent()

    data object NavigateToGithubSourceCode: AboutAppUiEvent()

}