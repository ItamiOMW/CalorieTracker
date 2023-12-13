package com.itami.calorie_tracker.diary_feature.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ConsumedFoodRequest(
    val foodId: Int,
    val grams: Int,
)