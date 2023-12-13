package com.itami.calorie_tracker.diary_feature.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    primaryKeys = ["id", "mealId", "foodId"],
    tableName = "consumed_foods",
    foreignKeys = [
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = MealEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealId"],
            onDelete = CASCADE
        ),
    ],
    indices = [Index("foodId"), Index("mealId")]
)
data class ConsumedFoodEntity(
    val id: Int,
    val mealId: Int,
    val foodId: Int,
    val grams: Int,
)
