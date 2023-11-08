package com.itami.calorie_tracker.authentication_feature.presentation.screens.age

sealed class AgeEvent {

    data class AgeValueChange(val age: String) : AgeEvent()

    data object SaveAge : AgeEvent()

}