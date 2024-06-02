package com.itami.calorie_tracker.authentication_feature.presentation.screens.height

import com.itami.calorie_tracker.core.domain.model.HeightUnit

sealed class HeightAction {

    data class ChangeHeight(val centimeters: Int): HeightAction()

    data class ChangeHeightUnit(val heightUnit: HeightUnit) : HeightAction()

    data object NavigateNextClick : HeightAction()

    data object NavigateBackClick : HeightAction()

}
