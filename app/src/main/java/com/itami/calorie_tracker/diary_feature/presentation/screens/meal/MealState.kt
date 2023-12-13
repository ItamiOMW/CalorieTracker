package com.itami.calorie_tracker.diary_feature.presentation.screens.meal

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

data class MealState(
    val mealName: String = "",
    val consumedFoods: List<ConsumedFood> = emptyList(),
    val selectedConsumedFoodIndex: Int? = null,
    val showExitDialog: Boolean = false,
    val isLoading: Boolean = false,
)
