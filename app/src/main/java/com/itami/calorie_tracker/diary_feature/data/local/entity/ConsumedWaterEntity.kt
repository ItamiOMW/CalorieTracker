package com.itami.calorie_tracker.diary_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itami.calorie_tracker.diary_feature.data.local.converter.ZonedDateTimeConverter
import java.time.ZonedDateTime

@Entity(
    tableName = "consumed_water",
)
data class ConsumedWaterEntity(
    @PrimaryKey
    val id: Int,
    val waterMl: Int,
    @TypeConverters(ZonedDateTimeConverter::class)
    val timestamp: ZonedDateTime,
)
