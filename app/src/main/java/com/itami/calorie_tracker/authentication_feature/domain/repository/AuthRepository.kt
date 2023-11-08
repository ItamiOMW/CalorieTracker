package com.itami.calorie_tracker.authentication_feature.domain.repository

import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle
import com.itami.calorie_tracker.core.utils.AppResponse

interface AuthRepository {

    suspend fun registerGoogle(createUser: CreateUserGoogle): AppResponse<Unit>

    suspend fun loginGoogle(idToken: String): AppResponse<Unit>

    suspend fun isAuthenticated(): AppResponse<Unit>

}