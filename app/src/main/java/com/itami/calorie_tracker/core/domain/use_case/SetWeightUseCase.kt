package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class SetWeightUseCase(private val userManager: UserManager) {

    suspend operator fun invoke(
        weight: Float,
        unit: WeightUnit,
    ): AppResponse<Unit> {
        val weightGrams = when(unit) {
            WeightUnit.POUND -> {
                WeightUnit.POUND.poundsToGrams(pounds = weight)
            }
            WeightUnit.KILOGRAM -> {
                WeightUnit.KILOGRAM.kilogramsToGrams(kilograms = weight)
            }
        }
        val weightException = ValidationUtil.validateWeight(weightGrams = weightGrams)
        if (weightException != null) {
            return AppResponse.failed(appException = weightException)
        }
        userManager.setWeight(weightGrams = weightGrams)
        return AppResponse.success(Unit)
    }

}