package com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

data class NewMealState(
    val mealName: String = "",
    val consumedFoods: List<ConsumedFood> = emptyList(),
    val selectedConsumedFoodIndex: Int? = null,
    val showExitDialog: Boolean = false,
    val isLoading: Boolean = false,
)
