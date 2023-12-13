package com.itami.calorie_tracker.diary_feature.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class FoodResponse(
    val id: Int,
    val name: String,
    val caloriesIn100Grams: Int,
    val proteinsIn100Grams: Int,
    val fatsIn100Grams: Int,
    val carbsIn100Grams: Int,
)