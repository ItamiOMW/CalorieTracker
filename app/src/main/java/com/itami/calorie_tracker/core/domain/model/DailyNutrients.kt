package com.itami.calorie_tracker.core.domain.model

data class DailyNutrients(
    val calories: Int,
    val proteins: Int,
    val fats: Int,
    val carbs: Int,
    val waterMl: Int,
)
