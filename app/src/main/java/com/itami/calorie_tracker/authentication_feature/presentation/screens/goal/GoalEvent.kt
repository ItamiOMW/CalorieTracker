package com.itami.calorie_tracker.authentication_feature.presentation.screens.goal

import com.itami.calorie_tracker.core.domain.model.WeightGoal

sealed class GoalEvent {

    data class SelectGoal(val weightGoal: WeightGoal) : GoalEvent()

    data object SaveGoal : GoalEvent()

}
