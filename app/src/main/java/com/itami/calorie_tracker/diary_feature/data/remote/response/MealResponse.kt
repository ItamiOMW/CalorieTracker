package com.itami.calorie_tracker.diary_feature.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    val id: Int,
    val name: String,
    val userId: Int,
    val consumedFoods: List<ConsumedFoodResponse>,
    val createdAt: String,
)
