package com.itami.calorie_tracker.reports_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.reports_feature.data.remote.dto.response.WeightResponse
import com.itami.calorie_tracker.reports_feature.data.remote.dto.request.AddWeightRequest
import com.itami.calorie_tracker.reports_feature.data.remote.dto.request.EditWeightRequest

interface ReportsApiService {

    suspend fun getWeights(
        token: String,
    ): ApiResponse<List<WeightResponse>, ErrorResponse>

    suspend fun addWeight(
        token: String,
        addWeightRequest: AddWeightRequest
    ): ApiResponse<WeightResponse, ErrorResponse>

    suspend fun editWeight(
        token: String,
        weightId: Int,
        editWeightRequest: EditWeightRequest
    ): ApiResponse<WeightResponse, ErrorResponse>

}