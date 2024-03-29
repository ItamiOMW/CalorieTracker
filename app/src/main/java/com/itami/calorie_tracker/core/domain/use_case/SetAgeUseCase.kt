package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class SetAgeUseCase(private val userManager: UserManager) {

    suspend operator fun invoke(age: Int): AppResponse<Unit> {
        val ageException = ValidationUtil.validateAge(age)
        if (ageException != null) {
            return AppResponse.failed(appException = ageException)
        }
        userManager.setAge(age = age)
        return AppResponse.success(Unit)
    }

}