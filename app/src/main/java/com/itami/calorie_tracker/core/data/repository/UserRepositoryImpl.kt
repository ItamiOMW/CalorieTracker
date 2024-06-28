package com.itami.calorie_tracker.core.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.mapper.toUpdateUserRequest
import com.itami.calorie_tracker.core.data.mapper.toUser
import com.itami.calorie_tracker.core.data.remote.UserApiService
import com.itami.calorie_tracker.core.data.remote.request.ChangePasswordRequest
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.model.UpdateUser
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.repository.UserRepository
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userManager: UserManager,
    private val authManager: AuthManager,
    private val userApiService: UserApiService,
    @ApplicationContext private val context: Context,
) : UserRepository {

    private val jwtToken get() = authManager.token

    override suspend fun updateUser(updateUser: UpdateUser): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)

        val imageByteArray = updateUser.profilePictureUri?.toUri()
            ?.let { context.contentResolver.openInputStream(it) }
            ?.readBytes()

        return when (val response = userApiService.updateUser(
            token = token,
            updateUserRequest = updateUser.toUpdateUserRequest(),
            profileImageByteArray = imageByteArray
        )) {
            is ApiResponse.Success -> {
                val user = response.body.toUser()
                userManager.setUser(user)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (response.code) {
                    HttpStatusCode.TooManyRequests.value -> {
                        AppResponse.failed(
                            appException = AppException.TooManyRequestsException,
                            message = context.getString(R.string.error_too_many_requests)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            appException = AppException.GeneralException,
                            message = response.errorBody?.errorMessage
                                ?: context.getString(R.string.error_unknown)
                        )
                    }
                }
            }

            is ApiResponse.Error.HttpServerError -> {
                AppResponse.failed(
                    appException = AppException.ServerError,
                    message = context.getString(R.string.error_server)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(
                    appException = AppException.NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    appException = AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun deleteUser(): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        return when (val response = userApiService.deleteAccount(token = token)) {
            is ApiResponse.Success -> {
                userManager.setUser(null)
                authManager.setToken(null)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (response.code) {
                    HttpStatusCode.TooManyRequests.value -> {
                        AppResponse.failed(
                            appException = AppException.TooManyRequestsException,
                            message = context.getString(R.string.error_too_many_requests)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            appException = AppException.GeneralException,
                            message = response.errorBody?.errorMessage
                                ?: context.getString(R.string.error_unknown)
                        )
                    }
                }
            }

            is ApiResponse.Error.HttpServerError -> {
                AppResponse.failed(
                    appException = AppException.ServerError,
                    message = context.getString(R.string.error_server)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(
                    appException = AppException.NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    appException = AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
    ): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        val changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword)
        return when (val response = userApiService.changePassword(
            token = token,
            changePasswordRequest = changePasswordRequest
        )
        ) {
            is ApiResponse.Success -> {
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (response.code) {
                    HttpStatusCode.TooManyRequests.value -> {
                        AppResponse.failed(
                            appException = AppException.TooManyRequestsException,
                            message = context.getString(R.string.error_too_many_requests)
                        )
                    }

                    HttpStatusCode.Conflict.value -> {
                        AppResponse.failed(
                            appException = AppException.UnauthorizedException,
                            message = context.getString(R.string.error_your_account_does_not_have_password)
                        )
                    }

                    HttpStatusCode.Unauthorized.value -> {
                        AppResponse.failed(
                            appException = AppException.UnauthorizedException,
                            message = context.getString(R.string.error_invalid_old_password)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            appException = AppException.GeneralException,
                            message = response.errorBody?.errorMessage
                                ?: context.getString(R.string.error_unknown)
                        )
                    }
                }
            }

            is ApiResponse.Error.HttpServerError -> {
                AppResponse.failed(
                    appException = AppException.ServerError,
                    message = context.getString(R.string.error_server)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(
                    appException = AppException.NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    appException = AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }
}