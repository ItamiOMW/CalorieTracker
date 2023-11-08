package com.itami.calorie_tracker.authentication_feature.presentation.screens.height

sealed class HeightUiEvent {

    data object HeightSaved: HeightUiEvent()

    data class ShowSnackbar(val message: String): HeightUiEvent()

}
