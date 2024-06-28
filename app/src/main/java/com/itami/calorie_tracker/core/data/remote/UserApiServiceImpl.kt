package com.itami.calorie_tracker.core.data.remote

import com.itami.calorie_tracker.core.data.remote.request.ChangePasswordRequest
import com.itami.calorie_tracker.core.data.remote.request.UpdateUserRequest
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

class UserApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : UserApiService {

    companion object {

        private const val USER = "/api/v1/user"

        private const val USER_CHANGE_PASSWORD = "$USER/change-password"

    }

    @OptIn(InternalAPI::class)
    override suspend fun updateUser(
        token: String,
        updateUserRequest: UpdateUserRequest,
        profileImageByteArray: ByteArray?,
    ): ApiResponse<UserResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(USER)
            method = HttpMethod.Put
            bearerAuth(token)
            timeout {
                requestTimeoutMillis = 120000
                socketTimeoutMillis = 120000
            }
            body = MultiPartFormDataContent(
                formData {
                    append(
                        key = "update_user_request",
                        value = Json.encodeToString(
                            UpdateUserRequest.serializer(),
                            updateUserRequest
                        ),
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

    override suspend fun deleteAccount(token: String): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(USER)
            method = HttpMethod.Delete
            bearerAuth(token)
        }
    }

    override suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url(USER_CHANGE_PASSWORD)
            method = HttpMethod.Post
            bearerAuth(token)
            setBody(changePasswordRequest)
        }
    }
}