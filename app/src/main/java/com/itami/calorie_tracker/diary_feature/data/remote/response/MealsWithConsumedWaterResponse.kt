package com.itami.calorie_tracker.diary_feature.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MealsWithConsumedWaterResponse(
    val meals: List<MealResponse>,
    val consumedWater: ConsumedWaterResponse? = null
)
