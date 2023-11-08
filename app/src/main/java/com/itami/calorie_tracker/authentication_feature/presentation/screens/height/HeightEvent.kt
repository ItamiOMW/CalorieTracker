package com.itami.calorie_tracker.authentication_feature.presentation.screens.height

import com.itami.calorie_tracker.core.domain.model.HeightUnit

sealed class HeightEvent {

    data class ChangeHeight(val centimeters: Int): HeightEvent()

    data class ChangeHeightUnit(val heightUnit: HeightUnit) : HeightEvent()

    data object SaveHeight : HeightEvent()

}
