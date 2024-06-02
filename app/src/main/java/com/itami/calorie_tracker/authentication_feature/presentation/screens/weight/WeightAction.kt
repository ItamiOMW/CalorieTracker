package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

import com.itami.calorie_tracker.core.domain.model.WeightUnit

sealed class WeightAction {

    data class ChangeWeightUnit(val weightUnit: WeightUnit) : WeightAction()

    data class WeightValueChange(val weight: String) : WeightAction()

    data object NavigateNextClick : WeightAction()

    data object NavigateBackClick : WeightAction()

}