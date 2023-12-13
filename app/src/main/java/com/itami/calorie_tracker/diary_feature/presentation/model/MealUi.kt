package com.itami.calorie_tracker.diary_feature.presentation.model

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import java.time.LocalDateTime

data class MealUi(
    val id: Int,
    val name: String,
    val consumedFoods: List<ConsumedFood>,
    val createdAt: LocalDateTime,
    val proteins: Int,
    val fats: Int,
    val carbs: Int,
    val calories: Int,
)
