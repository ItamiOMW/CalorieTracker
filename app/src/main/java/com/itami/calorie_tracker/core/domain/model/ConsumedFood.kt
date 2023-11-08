package com.itami.calorie_tracker.core.domain.model

data class ConsumedFood(
    val id: Int,
    val food: Food,
    val grams: Int,
)