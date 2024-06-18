package com.itami.calorie_tracker.profile_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.profile_feature.data.remote.request.FeedbackMessageRequest

interface FeedbackApiService {

    suspend fun sendFeedbackMessage(
        token: String,
        feedbackMessageRequest: FeedbackMessageRequest,
    ): ApiResponse<Unit, ErrorResponse>

}