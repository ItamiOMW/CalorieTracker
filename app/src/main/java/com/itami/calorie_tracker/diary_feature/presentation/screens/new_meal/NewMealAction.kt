package com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

sealed class NewMealAction {

    data class MealNameChange(val newValue: String) : NewMealAction()

    data class AddConsumedFoodRequest(val consumedFood: ConsumedFood): NewMealAction()

    data object SaveMealClick : NewMealAction()

    data class SelectConsumedFood(val index: Int?): NewMealAction()

    data class UpdateConsumedFood(val index: Int, val weightGrams: Int): NewMealAction()

    data class DeleteConsumedFood(val index: Int): NewMealAction()

    data object NavigateBackClick : NewMealAction()

    data object AddFoodIconClick : NewMealAction()

    data object NavigateBackConfirmClick: NewMealAction()

    data object NavigateBackDenyClick: NewMealAction()

}
