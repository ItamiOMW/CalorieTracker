package com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle

import com.itami.calorie_tracker.core.domain.model.Lifestyle

sealed class LifestyleEvent {

    data class SelectLifestyle(val lifestyle: Lifestyle) : LifestyleEvent()

    data object SaveLifestyle : LifestyleEvent()

}
