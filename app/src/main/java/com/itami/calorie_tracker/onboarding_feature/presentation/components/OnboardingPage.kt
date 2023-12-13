package com.itami.calorie_tracker.onboarding_feature.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.itami.calorie_tracker.R

sealed class OnboardingPage(
    @DrawableRes val lightImageId: Int,
    @DrawableRes val darkImageId: Int,
    @StringRes val titleId: Int,
    @StringRes val descId: Int
) {

    data object Welcoming: OnboardingPage(
        lightImageId = R.drawable.illustration_welcoming_light,
        darkImageId = R.drawable.illustration_welcoming_dark,
        titleId = R.string.welcome_em,
        descId = R.string.welcome_onboarding_description
    )

    data object Tracking: OnboardingPage(
        lightImageId = R.drawable.illustration_tracking_light,
        darkImageId = R.drawable.illustration_tracking_dark,
        titleId = R.string.title_effortless_tracking,
        descId = R.string.tracking_onboarding_description
    )

    data object GoalSetting: OnboardingPage(
        lightImageId = R.drawable.illustration_progress_light,
        darkImageId = R.drawable.illustration_progress_dark,
        titleId = R.string.title_effortless_tracking,
        descId = R.string.goal_setting_onboarding_description
    )

}