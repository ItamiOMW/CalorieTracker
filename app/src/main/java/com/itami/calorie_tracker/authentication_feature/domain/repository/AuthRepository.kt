package com.itami.calorie_tracker.authentication_feature.domain.repository

import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserEmail
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle
import com.itami.calorie_tracker.core.utils.AppResponse

interface AuthRepository {

    suspend fun registerEmail(createUser: CreateUserEmail): AppResponse<Unit>

    suspend fun loginEmail(email: String, password: String): AppResponse<Unit>

    suspend fun resendActivationEmail(email: String): AppResponse<Unit>

    suspend fun sendPasswordResetCode(email: String): AppResponse<Unit>

    suspend fun resetPassword(email: String, resetCode: Int, newPassword: String): AppResponse<Unit>

    suspend fun registerGoogle(createUser: CreateUserGoogle): AppResponse<Unit>

    suspend fun loginGoogle(idToken: String): AppResponse<Unit>

    suspend fun isAuthenticated(): AppResponse<Unit>

}