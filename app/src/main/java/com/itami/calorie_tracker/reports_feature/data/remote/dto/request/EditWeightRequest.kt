package com.itami.calorie_tracker.reports_feature.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class EditWeightRequest(
    val weightGrams: Int,
)