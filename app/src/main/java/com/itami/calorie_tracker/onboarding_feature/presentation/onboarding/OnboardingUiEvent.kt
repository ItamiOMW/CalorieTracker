package com.itami.calorie_tracker.onboarding_feature.presentation.onboarding

sealed class OnboardingUiEvent {

    data object ShowOnboardingStateSaved: OnboardingUiEvent()

}
