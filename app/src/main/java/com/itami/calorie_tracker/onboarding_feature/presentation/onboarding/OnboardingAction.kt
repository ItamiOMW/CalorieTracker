package com.itami.calorie_tracker.onboarding_feature.presentation.onboarding

sealed class OnboardingAction {

    data object NavigateNextClick : OnboardingAction()

    data object SkipClick : OnboardingAction()

}
