package com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

sealed class NewMealAction {

    data class MealNameChange(val newValue: String) : NewMealAction()

    data class AddConsumedFood(val consumedFood: ConsumedFood): NewMealAction()

    data object SaveMeal : NewMealAction()

    data class ShowExitDialog(val show: Boolean) : NewMealAction()

    data class SelectConsumedFood(val index: Int?): NewMealAction()

    data class UpdateConsumedFood(val index: Int, val weightGrams: Int): NewMealAction()

    data class DeleteConsumedFood(val index: Int): NewMealAction()

}
