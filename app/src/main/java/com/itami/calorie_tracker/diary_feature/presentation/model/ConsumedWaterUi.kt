package com.itami.calorie_tracker.diary_feature.presentation.model

import java.time.LocalDateTime

data class ConsumedWaterUi(
    val id: Int,
    val timestamp: LocalDateTime,
    val waterMl: Int,
)