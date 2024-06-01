package com.itami.calorie_tracker.diary_feature.presentation.screens.meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

sealed class MealAction {

    data class MealNameChange(val newValue: String) : MealAction()

    data class AddConsumedFood(val consumedFood: ConsumedFood): MealAction()

    data object SaveMeal : MealAction()

    data class ShowExitDialog(val show: Boolean) : MealAction()

    data class SelectConsumedFood(val index: Int?): MealAction()

    data class UpdateConsumedFood(val index: Int, val weightGrams: Int): MealAction()

    data class DeleteConsumedFood(val index: Int): MealAction()

}
