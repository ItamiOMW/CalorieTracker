package com.itami.calorie_tracker.diary_feature.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ConsumedFoodWithFood(
    @Embedded val consumedFood: ConsumedFoodEntity,
    @Relation(
        parentColumn = "foodId",
        entityColumn = "id",
    )
    val food: FoodEntity,
)
