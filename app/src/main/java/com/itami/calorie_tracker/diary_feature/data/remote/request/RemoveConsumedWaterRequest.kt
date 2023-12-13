package com.itami.calorie_tracker.diary_feature.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RemoveConsumedWaterRequest(
    val datetime: String,
    val waterMlToRemove: Int,
)
