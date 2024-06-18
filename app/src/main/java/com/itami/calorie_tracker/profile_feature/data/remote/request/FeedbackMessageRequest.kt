package com.itami.calorie_tracker.profile_feature.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackMessageRequest(
    val message: String
)
