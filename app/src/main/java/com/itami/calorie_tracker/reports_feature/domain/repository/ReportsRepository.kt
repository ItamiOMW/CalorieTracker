package com.itami.calorie_tracker.reports_feature.domain.repository

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.reports_feature.domain.model.Weight

interface ReportsRepository {

    suspend fun getWeights(): AppResponse<List<Weight>>

    suspend fun addWeight(datetime: String, weightGrams: Int): AppResponse<Weight>

    suspend fun editWeight(weightId: Int, weightGrams: Int): AppResponse<Weight>

}