package com.itami.calorie_tracker.diary_feature.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithConsumedFoods(
    @Embedded val mealEntity: MealEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "mealId",
        entity = ConsumedFoodEntity::class,
    )
    val consumedFoods: List<ConsumedFoodWithFood>
)
