package com.itami.calorie_tracker.authentication_feature.presentation.screens.goal

import com.itami.calorie_tracker.core.domain.model.WeightGoal

sealed class GoalAction {

    data class SelectGoal(val weightGoal: WeightGoal) : GoalAction()

    data object NavigateNextClick : GoalAction()

}
