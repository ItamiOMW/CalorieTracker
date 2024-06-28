package com.itami.calorie_tracker.settings_feature.presentation.screens.settings

sealed class SettingsUiEvent {

   data object NavigateToAccount : SettingsUiEvent()

   data object NavigateToLanguage : SettingsUiEvent()

   data object NavigateToSubscription : SettingsUiEvent()

    data object NavigateBack : SettingsUiEvent()

}