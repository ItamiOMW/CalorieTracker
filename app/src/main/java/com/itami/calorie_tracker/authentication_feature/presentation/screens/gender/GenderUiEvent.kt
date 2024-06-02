package com.itami.calorie_tracker.authentication_feature.presentation.screens.gender

sealed class GenderUiEvent {

    data object GenderSaved: GenderUiEvent()

    data object NavigateBack: GenderUiEvent()

}
