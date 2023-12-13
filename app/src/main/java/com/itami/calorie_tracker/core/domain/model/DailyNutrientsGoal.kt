package com.itami.calorie_tracker.core.domain.model

data class DailyNutrientsGoal(
    val caloriesGoal: Int,
    val proteinsGoal: Int,
    val fatsGoal: Int,
    val carbsGoal: Int,
    val waterMlGoal: Int,
)
