package com.itami.calorie_tracker.authentication_feature.data.remote

import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleLoginRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleRegisterRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.response.UserWithTokenResponse
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import com.itami.calorie_tracker.core.data.remote.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

class AuthApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : AuthApiService {

    companion object {
        private const val AUTH = "/api/v1/auth"
        private const val REGISTER_GOOGLE = "$AUTH/google/register"
        private const val LOGIN_GOOGLE = "$AUTH/google/login"
    }

    override suspend fun registerGoogle(registerRequest: GoogleRegisterRequest): ApiResponse<UserWithTokenResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(REGISTER_GOOGLE)
            method = HttpMethod.Post
            setBody(registerRequest)
        }
    }

    override suspend fun loginGoogle(loginRequest: GoogleLoginRequest): ApiResponse<UserWithTokenResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(LOGIN_GOOGLE)
            method = HttpMethod.Post
            setBody(loginRequest)
        }
    }

    override suspend fun authenticate(token: String): ApiResponse<UserResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(AUTH)
            method = HttpMethod.Get
            bearerAuth(token)
        }
    }

}