package com.itami.calorie_tracker.diary_feature.presentation.screens.meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

sealed class MealEvent {

    data class MealNameChange(val newValue: String) : MealEvent()

    data class AddConsumedFood(val consumedFood: ConsumedFood): MealEvent()

    data object SaveMeal : MealEvent()

    data class ShowExitDialog(val show: Boolean) : MealEvent()

    data class SelectConsumedFood(val index: Int?): MealEvent()

    data class UpdateConsumedFood(val index: Int, val weightGrams: Int): MealEvent()

    data class DeleteConsumedFood(val index: Int): MealEvent()

}
