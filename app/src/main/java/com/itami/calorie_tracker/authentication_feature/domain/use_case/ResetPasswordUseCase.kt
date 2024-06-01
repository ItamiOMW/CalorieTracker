package com.itami.calorie_tracker.authentication_feature.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.model.ResetPasswordResult
import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class ResetPasswordUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(
        resetCode: Int,
        email: String,
        newPassword: String,
        repeatNewPassword: String,
    ): ResetPasswordResult {
        val trimmedEmail = email.trim()
        val trimmedPassword = newPassword.trim()
        val trimmedRepeatPassword = repeatNewPassword.trim()

        val passwordException = ValidationUtil.validatePassword(trimmedPassword)
        val repeatPasswordException = ValidationUtil.validatePasswords(trimmedPassword, trimmedRepeatPassword)
        val codeException = ValidationUtil.validateResetCode(resetCode)

        if (passwordException != null || codeException != null || repeatPasswordException != null) {
            return ResetPasswordResult(
                passwordException = passwordException,
                repeatPasswordException = repeatPasswordException,
                codeException = codeException
            )
        }

        val response = authRepository.resetPassword(trimmedEmail, resetCode, trimmedPassword)
        return ResetPasswordResult(response = response)
    }

}