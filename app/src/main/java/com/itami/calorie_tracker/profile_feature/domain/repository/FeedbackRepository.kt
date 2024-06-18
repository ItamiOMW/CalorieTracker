package com.itami.calorie_tracker.profile_feature.domain.repository

import com.itami.calorie_tracker.core.utils.AppResponse

interface FeedbackRepository {

    suspend fun sendFeedback(message: String): AppResponse<Unit>

}