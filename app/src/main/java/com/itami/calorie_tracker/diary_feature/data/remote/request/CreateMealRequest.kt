package com.itami.calorie_tracker.diary_feature.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateMealRequest(
    val name: String,
    val consumedFoods: List<ConsumedFoodRequest>,
    val datetime: String,
)