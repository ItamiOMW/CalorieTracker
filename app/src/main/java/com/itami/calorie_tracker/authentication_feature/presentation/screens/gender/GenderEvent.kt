package com.itami.calorie_tracker.authentication_feature.presentation.screens.gender

import com.itami.calorie_tracker.core.domain.model.Gender

sealed class GenderEvent {

    data class SelectGender(val gender: Gender) : GenderEvent()

    data object SaveGender : GenderEvent()

}
