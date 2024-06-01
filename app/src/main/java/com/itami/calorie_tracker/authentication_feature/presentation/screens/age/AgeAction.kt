package com.itami.calorie_tracker.authentication_feature.presentation.screens.age

sealed class AgeAction {

    data class AgeValueChange(val age: String) : AgeAction()

    data object SaveAge : AgeAction()

}