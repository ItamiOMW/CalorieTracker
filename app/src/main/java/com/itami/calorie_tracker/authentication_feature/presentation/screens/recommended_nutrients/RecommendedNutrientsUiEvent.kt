package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

sealed class RecommendedNutrientsUiEvent {

    data class ShowSnackbar(val message: String): RecommendedNutrientsUiEvent()

    data object GoogleRegisterSuccessful: RecommendedNutrientsUiEvent()

    data object NavigateToRegisterEmail: RecommendedNutrientsUiEvent()

}
