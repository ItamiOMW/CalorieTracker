package com.itami.calorie_tracker.core.domain.model

data class UpdateUser(
    val name: String? = null,
    val profilePictureUri: String? = null,
    val age: Int? = null,
    val heightCm: Int? = null,
    val weightGrams: Int? = null,
    val lifestyle: Lifestyle? = null,
    val gender: Gender? = null,
    val weightGoal: WeightGoal? = null,
    val dailyNutrientsGoal: DailyNutrientsGoal? = null,
)
