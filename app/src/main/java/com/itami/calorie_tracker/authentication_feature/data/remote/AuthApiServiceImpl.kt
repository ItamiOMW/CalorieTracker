package com.itami.calorie_tracker.authentication_feature.data.remote

import com.itami.calorie_tracker.authentication_feature.data.remote.request.EmailLoginRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.EmailRegisterRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleLoginRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleRegisterRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.ResendActivationEmailRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.ResetPasswordRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.SendPasswordResetCodeRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.response.UserWithTokenResponse
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import com.itami.calorie_tracker.core.data.remote.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : AuthApiService {

    companion object {
        private const val AUTH = "/api/v1/auth"

        private const val REGISTER_EMAIL = "$AUTH/email/register"
        private const val LOGIN_EMAIL = "$AUTH/email/login"

        private const val REGISTER_GOOGLE = "$AUTH/google/register"
        private const val LOGIN_GOOGLE = "$AUTH/google/login"

        private const val RESEND_CONFIRMATION_EMAIL = "/activate/resend"

        private const val RESET_PASSWORD = "$AUTH/reset/password"
        private const val SEND_PASSWORD_RESET = "$AUTH/reset/password/send"
    }

    @OptIn(InternalAPI::class)
    override suspend fun registerEmail(
        registerRequest: EmailRegisterRequest,
        profileImageByteArray: ByteArray?,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(REGISTER_EMAIL)
            timeout {
                requestTimeoutMillis = 120000
                socketTimeoutMillis = 120000
            }
            method = HttpMethod.Post
            body = MultiPartFormDataContent(
                formData {
                    append(
                        key = "email_register_request",
                        value = Json.encodeToString(EmailRegisterRequest.serializer(), registerRequest),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        })
                    profileImageByteArray?.let { byteArray ->
                        append(
                            key = "profile_picture",
                            value = byteArray,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=image.png")
                            })
                    }
                }
            )
        }
    }

    override suspend fun loginEmail(loginRequest: EmailLoginRequest): ApiResponse<UserWithTokenResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(LOGIN_EMAIL)
            method = HttpMethod.Post
            setBody(loginRequest)
        }
    }

    override suspend fun resendActivationEmail(
        resendActivationEmailRequest: ResendActivationEmailRequest,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(RESEND_CONFIRMATION_EMAIL)
            timeout {
                requestTimeoutMillis = 120000
            }
            method = HttpMethod.Post
            setBody(resendActivationEmailRequest)
        }
    }

    override suspend fun sendPasswordReset(
        sendPasswordResetCodeRequest: SendPasswordResetCodeRequest,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(SEND_PASSWORD_RESET)
            timeout {
                requestTimeoutMillis = 120000
            }
            method = HttpMethod.Post
            setBody(sendPasswordResetCodeRequest)
        }
    }

    override suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(RESET_PASSWORD)
            method = HttpMethod.Post
            setBody(resetPasswordRequest)
        }
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