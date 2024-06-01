package com.itami.calorie_tracker.authentication_feature.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserEmail
import com.itami.calorie_tracker.authentication_feature.domain.model.RegisterResult
import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class RegisterEmailUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(
        createUserEmail: CreateUserEmail,
    ): RegisterResult {
        val newCreateUserEmail = createUserEmail.copy(
            email = createUserEmail.email.trim(),
            password = createUserEmail.password.trim(),
            name = createUserEmail.name.trim()
        )

        val nameException = ValidationUtil.validateName(name = newCreateUserEmail.name)
        val emailException = ValidationUtil.validateEmail(email = newCreateUserEmail.email)
        val passwordException =
            ValidationUtil.validatePassword(password = newCreateUserEmail.password)

        if (emailException != null || passwordException != null || nameException != null) {
            return RegisterResult(
                emailException = emailException,
                passwordException = passwordException,
                nameException = nameException
            )
        }

        val response = authRepository.registerEmail(newCreateUserEmail)
        return RegisterResult(response = response)
    }

}