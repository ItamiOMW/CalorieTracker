package com.itami.calorie_tracker.authentication_feature.data.remote

import com.itami.calorie_tracker.authentication_feature.data.remote.request.EmailLoginRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.EmailRegisterRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleLoginRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleRegisterRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.ResendActivationEmailRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.ResetPasswordRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.SendPasswordResetCodeRequest
import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import com.itami.calorie_tracker.authentication_feature.data.remote.response.UserWithTokenResponse
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse

interface AuthApiService {

    suspend fun registerEmail(
        registerRequest: EmailRegisterRequest,
        profileImageByteArray: ByteArray?,
    ): ApiResponse<Unit, ErrorResponse>

    suspend fun loginEmail(
        loginRequest: EmailLoginRequest
    ): ApiResponse<UserWithTokenResponse, ErrorResponse>

    suspend fun resendActivationEmail(
        resendActivationEmailRequest: ResendActivationEmailRequest,
    ): ApiResponse<Unit, ErrorResponse>

    suspend fun sendPasswordReset(
        sendPasswordResetCodeRequest: SendPasswordResetCodeRequest
    ): ApiResponse<Unit, ErrorResponse>

    suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest
    ): ApiResponse<Unit, ErrorResponse>

    suspend fun registerGoogle(
        registerRequest: GoogleRegisterRequest
    ): ApiResponse<UserWithTokenResponse, ErrorResponse>

    suspend fun loginGoogle(
        loginRequest: GoogleLoginRequest
    ): ApiResponse<UserWithTokenResponse, ErrorResponse>

    suspend fun authenticate(
        token: String
    ): ApiResponse<UserResponse, ErrorResponse>

}