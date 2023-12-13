package com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

sealed class NewMealEvent {

    data class MealNameChange(val newValue: String) : NewMealEvent()

    data class AddConsumedFood(val consumedFood: ConsumedFood): NewMealEvent()

    data object SaveMeal : NewMealEvent()

    data class ShowExitDialog(val show: Boolean) : NewMealEvent()

    data class SelectConsumedFood(val index: Int?): NewMealEvent()

    data class UpdateConsumedFood(val index: Int, val weightGrams: Int): NewMealEvent()

    data class DeleteConsumedFood(val index: Int): NewMealEvent()

}
