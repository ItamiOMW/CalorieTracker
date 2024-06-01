package com.itami.calorie_tracker.authentication_feature.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.model.LoginResult
import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class LoginEmailUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): LoginResult {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()
        val emailException = ValidationUtil.validateEmail(email = trimmedEmail)
        // There is no need to validate the password, as this is the login screen, not the registration screen.
        val passwordException = if (trimmedPassword.isEmpty()) UserCredentialsException.EmptyPasswordFieldException else null

        if (emailException != null || passwordException != null) {
            return LoginResult(
                emailException = emailException,
                passwordException = passwordException
            )
        }

        val response = authRepository.loginEmail(
            email = trimmedEmail,
            password = trimmedPassword
        )
        return LoginResult(response = response)
    }

}