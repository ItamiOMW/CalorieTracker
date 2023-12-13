package com.itami.calorie_tracker.diary_feature.domain.model

import com.itami.calorie_tracker.core.utils.Constants

data class ConsumedFood(
    val id: Int = Constants.UNKNOWN_ID,
    val food: Food,
    val grams: Int = Constants.DEFAULT_FOOD_SERVING_GRAMS,
)