package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

sealed class RecommendedNutrientEvent {

    data class SignUpWithGoogle(val idToken: String): RecommendedNutrientEvent()

    data class ShowGoogleOneTap(val show: Boolean): RecommendedNutrientEvent()

}
