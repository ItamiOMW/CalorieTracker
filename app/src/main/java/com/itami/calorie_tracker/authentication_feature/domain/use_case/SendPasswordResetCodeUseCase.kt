package com.itami.calorie_tracker.authentication_feature.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil
import com.itami.calorie_tracker.core.utils.AppResponse

class SendPasswordResetCodeUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String): AppResponse<Unit> {
        val trimmedEmail = email.trim()

        val emailException = ValidationUtil.validateEmail(trimmedEmail)

        if (emailException != null) {
            return AppResponse.failed(emailException)
        }

        return authRepository.sendPasswordResetCode(trimmedEmail)
    }

}