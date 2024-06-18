package com.itami.calorie_tracker.profile_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.utils.safeRequest
import com.itami.calorie_tracker.profile_feature.data.remote.request.FeedbackMessageRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

class FeedbackApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : FeedbackApiService {

    companion object {

        private const val FEEDBACK = "/api/v1/feedback"

    }

    override suspend fun sendFeedbackMessage(
        token: String,
        feedbackMessageRequest: FeedbackMessageRequest,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(FEEDBACK)
            method = HttpMethod.Post
            bearerAuth(token)
            setBody(feedbackMessageRequest)
        }
    }

}