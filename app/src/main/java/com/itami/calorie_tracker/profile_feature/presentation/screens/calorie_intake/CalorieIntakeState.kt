package com.itami.calorie_tracker.profile_feature.presentation.screens.calorie_intake

import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal

data class CalorieIntakeState(
    val recommendedNutrients: DailyNutrientsGoal = DailyNutrientsGoal(0, 0, 0, 0, 0),
    val currentNutrients: DailyNutrientsGoal = DailyNutrientsGoal(0, 0, 0, 0, 0),
    val sheetContent: CalorieIntakeScreenSheetContent? = null,
    val isLoading: Boolean = false,
)

enum class CalorieIntakeScreenSheetContent {
    CALORIES, PROTEINS, FATS, CARBS, WATER;
}