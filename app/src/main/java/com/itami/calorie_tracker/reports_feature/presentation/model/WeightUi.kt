package com.itami.calorie_tracker.reports_feature.presentation.model

import java.time.LocalDateTime

data class WeightUi(
    val id: Int,
    val weightGrams: Int,
    val weightKgs: Float,
    val weightPounds: Float,
    val datetime: LocalDateTime,
)
