package com.itami.calorie_tracker.reports_feature.domain.use_case

import com.itami.calorie_tracker.core.domain.utils.ValidationUtil
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.reports_feature.domain.model.Weight
import com.itami.calorie_tracker.reports_feature.domain.repository.ReportsRepository

class EditWeightUseCase(private val reportsRepository: ReportsRepository) {

    suspend operator fun invoke(
        weightGrams: Int,
        weightId: Int,
    ): AppResponse<Weight> {
        val weightException = ValidationUtil.validateWeight(weightGrams = weightGrams)
        if (weightException != null) {
            return AppResponse.failed(appException = weightException)
        }
        return reportsRepository.editWeight(
            weightId = weightId,
            weightGrams = weightGrams
        )
    }

}