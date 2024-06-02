package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

sealed class RecommendedNutrientsAction {

    data class GoogleIdTokenReceived(val idToken: String): RecommendedNutrientsAction()

    data object DismissGoogleOneTap : RecommendedNutrientsAction()

    data object ContinueWithGoogleClick : RecommendedNutrientsAction()
    
    data object ContinueWithEmailClick : RecommendedNutrientsAction()

}
