package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

sealed class WeightUiEvent {

    data object WeightSaved : WeightUiEvent()

    data object NavigateBack : WeightUiEvent()

    data class ShowSnackbar(val message: String) : WeightUiEvent()

}
