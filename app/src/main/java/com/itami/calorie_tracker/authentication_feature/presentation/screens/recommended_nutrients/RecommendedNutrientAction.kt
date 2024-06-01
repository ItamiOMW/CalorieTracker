package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

sealed class RecommendedNutrientAction {

    data class SignUpWithGoogle(val idToken: String): RecommendedNutrientAction()

    data class ShowGoogleOneTap(val show: Boolean): RecommendedNutrientAction()

}
