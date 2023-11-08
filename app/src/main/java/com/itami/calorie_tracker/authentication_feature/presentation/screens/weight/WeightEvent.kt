package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

import com.itami.calorie_tracker.core.domain.model.WeightUnit

sealed class WeightEvent {

    data class ChangeWeightUnit(val weightUnit: WeightUnit) : WeightEvent()

    data class WeightValueChange(val weight: String) : WeightEvent()

    data object SaveWeight : WeightEvent()

}