package com.itami.calorie_tracker.diary_feature.data.mapper

import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedWaterEntity
import com.itami.calorie_tracker.diary_feature.data.remote.response.ConsumedWaterResponse
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedWater
import java.time.ZonedDateTime


fun ConsumedWaterEntity.toConsumedWater() = ConsumedWater(
    id = this.id,
    timestamp = this.timestamp.toString(),
    waterMl = this.waterMl,
)

fun ConsumedWaterResponse.toConsumedWaterEntity() = ConsumedWaterEntity(
    id = this.id,
    timestamp = ZonedDateTime.parse(this.timestamp),
    waterMl = this.waterMl,
)