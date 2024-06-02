package com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle

import com.itami.calorie_tracker.core.domain.model.Lifestyle

sealed class LifestyleAction {

    data class SelectLifestyle(val lifestyle: Lifestyle) : LifestyleAction()

    data object NavigateNextClick : LifestyleAction()

    data object NavigateBackClick : LifestyleAction()

}
