package com.itami.calorie_tracker.profile_feature.presentation.screens.calorie_intake

sealed class CalorieIntakeUiEvent {

    data class ShowSnackbar(val message: String) : CalorieIntakeUiEvent()

    data object NavigateBack : CalorieIntakeUiEvent()

    data object CalorieIntakeSaved: CalorieIntakeUiEvent()

}