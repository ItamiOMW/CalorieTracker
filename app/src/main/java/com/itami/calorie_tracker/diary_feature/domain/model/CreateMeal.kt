package com.itami.calorie_tracker.diary_feature.domain.model

data class CreateMeal(
    val name: String,
    val consumedFoods: List<CreateConsumedFood>,
    val createAt: String,
)