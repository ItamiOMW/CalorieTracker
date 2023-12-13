package com.itami.calorie_tracker.diary_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val caloriesIn100Grams: Int,
    val proteinsIn100Grams: Int,
    val fatsIn100Grams: Int,
    val carbsIn100Grams: Int,
)
