package com.itami.calorie_tracker.authentication_feature.domain.use_case

import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle
import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.utils.AppResponse

class RegisterGoogleUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(createUserGoogle: CreateUserGoogle): AppResponse<Unit> {
        return authRepository.registerGoogle(createUser = createUserGoogle)
    }

}