package com.itami.calorie_tracker.onboarding_feature.presentation.onboarding

sealed class OnboardingEvent {

    data class ChangeShowOnboardingState(val show: Boolean): OnboardingEvent()

}
