package com.itami.calorie_tracker.authentication_feature.data.remote

import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleLoginRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleRegisterRequest
import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import com.itami.calorie_tracker.authentication_feature.data.remote.response.UserWithTokenResponse
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse

interface AuthApiService {

    suspend fun registerGoogle(registerRequest: GoogleRegisterRequest): ApiResponse<UserWithTokenResponse, ErrorResponse>

    suspend fun loginGoogle(loginRequest: GoogleLoginRequest): ApiResponse<UserWithTokenResponse, ErrorResponse>

    suspend fun authenticate(token: String): ApiResponse<UserResponse, ErrorResponse>

}