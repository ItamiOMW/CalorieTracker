package com.itami.calorie_tracker.diary_feature.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateMealRequest(
    val name: String,
    val consumedFoods: List<ConsumedFoodRequest>,
)