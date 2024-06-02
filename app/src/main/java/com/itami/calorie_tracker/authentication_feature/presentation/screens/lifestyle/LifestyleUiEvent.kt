package com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle

sealed class LifestyleUiEvent {

    data object LifestyleSaved: LifestyleUiEvent()

    data object NavigateBack: LifestyleUiEvent()

}
