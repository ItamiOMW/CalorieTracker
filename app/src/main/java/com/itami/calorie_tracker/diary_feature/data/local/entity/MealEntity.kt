package com.itami.calorie_tracker.diary_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itami.calorie_tracker.diary_feature.data.local.converter.ZonedDateTimeConverter
import java.time.ZonedDateTime

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @TypeConverters(ZonedDateTimeConverter::class)
    val utcCreatedAt: ZonedDateTime,
)
