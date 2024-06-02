package com.itami.calorie_tracker.authentication_feature.presentation.screens.age

sealed class AgeUiEvent {

    data object AgeSaved : AgeUiEvent()

    data object NavigateBack : AgeUiEvent()

    data class ShowSnackbar(val message: String) : AgeUiEvent()

}
