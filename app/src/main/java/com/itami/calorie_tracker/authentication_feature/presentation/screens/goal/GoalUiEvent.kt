package com.itami.calorie_tracker.authentication_feature.presentation.screens.goal

sealed class GoalUiEvent {

    data object GoalSaved: GoalUiEvent()

}
