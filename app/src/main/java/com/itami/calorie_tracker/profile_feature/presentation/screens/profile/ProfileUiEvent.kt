package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

sealed class ProfileUiEvent {

    data object NavigateBack: ProfileUiEvent()

    data object NavigateToMyInfo: ProfileUiEvent()

    data object NavigateToCalorieIntake: ProfileUiEvent()

    data object NavigateToWaterIntake: ProfileUiEvent()

    data object NavigateToSettings: ProfileUiEvent()

    data object NavigateToContactsUs: ProfileUiEvent()

    data object NavigateToAbout: ProfileUiEvent()

}