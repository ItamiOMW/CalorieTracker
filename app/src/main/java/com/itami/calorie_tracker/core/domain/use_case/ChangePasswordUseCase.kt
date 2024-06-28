package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.model.ChangePasswordResult
import com.itami.calorie_tracker.core.domain.repository.UserRepository
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class ChangePasswordUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        oldPassword: String,
        newPassword: String,
        repeatNewPassword: String,
    ): ChangePasswordResult {
        val trimmedOldPassword = oldPassword.trim()
        val trimmedNewPassword = newPassword.trim()
        val trimmedRepeatNewPassword = repeatNewPassword.trim()

        val oldPasswordException = ValidationUtil.validatePassword(trimmedOldPassword)
        val newPasswordException = ValidationUtil.validatePassword(trimmedNewPassword)
        val repeatNewPasswordException = ValidationUtil.validatePasswords(newPassword, trimmedRepeatNewPassword)

        if (oldPasswordException != null || newPasswordException != null || repeatNewPasswordException != null) {
            return ChangePasswordResult(
                oldPasswordException = oldPasswordException,
                newPasswordException = newPasswordException,
                repeatPasswordException = repeatNewPasswordException
            )
        }

        val response = userRepository.changePassword(oldPassword, newPassword)
        return ChangePasswordResult(response = response)
    }

}