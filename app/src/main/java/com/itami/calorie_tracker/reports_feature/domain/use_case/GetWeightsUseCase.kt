package com.itami.calorie_tracker.reports_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.reports_feature.domain.model.Weight
import com.itami.calorie_tracker.reports_feature.domain.repository.ReportsRepository

class GetWeightsUseCase(private val reportsRepository: ReportsRepository) {

    suspend operator fun invoke(): AppResponse<List<Weight>> {
        return reportsRepository.getWeights()
    }

}