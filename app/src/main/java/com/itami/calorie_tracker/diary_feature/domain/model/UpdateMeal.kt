package com.itami.calorie_tracker.diary_feature.domain.model

data class UpdateMeal(
    val name: String,
    val consumedFoods: List<CreateConsumedFood>,
)