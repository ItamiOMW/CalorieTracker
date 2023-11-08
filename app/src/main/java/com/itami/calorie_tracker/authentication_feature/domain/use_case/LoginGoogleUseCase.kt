package com.itami.calorie_tracker.authentication_feature.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.utils.AppResponse

class LoginGoogleUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(googleIdToken: String): AppResponse<Unit> {
        return authRepository.loginGoogle(idToken = googleIdToken)
    }

}