package com.itami.calorie_tracker.onboarding_feature.presentation.onboarding

sealed class OnboardingAction {

    data class ChangeShowOnboardingState(val show: Boolean): OnboardingAction()

}
