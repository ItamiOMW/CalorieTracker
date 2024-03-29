package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class SetHeightUseCase(private val userManager: UserManager) {

    suspend operator fun invoke(heightCm: Int): AppResponse<Unit> {
        val heightException = ValidationUtil.validateHeight(heightCm = heightCm)
        if (heightException != null) {
            return AppResponse.failed(appException = heightException)
        }
        userManager.setHeight(heightCm = heightCm)
        return AppResponse.success(Unit)
    }

}