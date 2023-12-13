package com.itami.calorie_tracker.diary_feature.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ConsumedFoodResponse(
    val id: Int,
    val food: FoodResponse,
    val grams: Int,
)