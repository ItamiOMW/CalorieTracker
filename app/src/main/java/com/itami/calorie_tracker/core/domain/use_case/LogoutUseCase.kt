package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.utils.AppResponse

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): AppResponse<Unit> {
        return authRepository.logout()
    }

}