package com.itami.calorie_tracker.authentication_feature.presentation.screens.gender

import com.itami.calorie_tracker.core.domain.model.Gender

sealed class GenderAction {

    data class GenderClick(val gender: Gender) : GenderAction()

    data object NavigateNextClick : GenderAction()

    data object NavigateBackClick : GenderAction()

}
