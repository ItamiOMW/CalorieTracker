package com.itami.calorie_tracker.reports_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.safeRequest
import com.itami.calorie_tracker.reports_feature.data.remote.dto.response.WeightResponse
import com.itami.calorie_tracker.reports_feature.data.remote.dto.request.AddWeightRequest
import com.itami.calorie_tracker.reports_feature.data.remote.dto.request.EditWeightRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

class ReportsApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : ReportsApiService {

    companion object {

        private const val USER_WEIGHTS = "/api/v1/user/weights"

    }

    override suspend fun getWeights(token: String): ApiResponse<List<WeightResponse>, ErrorResponse> {
        return httpClient.safeRequest {
            url(USER_WEIGHTS)
            method = HttpMethod.Get
            bearerAuth(token)
        }
    }

    override suspend fun addWeight(
        token: String,
        addWeightRequest: AddWeightRequest,
    ): ApiResponse<WeightResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(USER_WEIGHTS)
            method = HttpMethod.Post
            bearerAuth(token)
            setBody(addWeightRequest)
        }
    }

    override suspend fun editWeight(
        token: String,
        weightId: Int,
        editWeightRequest: EditWeightRequest,
    ): ApiResponse<WeightResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url("$USER_WEIGHTS/$weightId")
            method = HttpMethod.Put
            bearerAuth(token)
            setBody(editWeightRequest)
        }
    }

}