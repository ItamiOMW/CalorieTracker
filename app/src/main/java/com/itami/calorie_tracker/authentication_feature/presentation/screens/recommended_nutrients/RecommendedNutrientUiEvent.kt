package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

sealed class RecommendedNutrientUiEvent {

    data class ShowSnackbar(val message: String): RecommendedNutrientUiEvent()

    data object SignUpSuccessful: RecommendedNutrientUiEvent()

}
