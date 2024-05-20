package com.itami.calorie_tracker.reports_feature.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WeightResponse(
    val id: Int,
    val weightGrams: Int,
    val datetime: String,
)