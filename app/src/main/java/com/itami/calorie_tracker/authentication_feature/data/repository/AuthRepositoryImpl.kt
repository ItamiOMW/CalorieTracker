package com.itami.calorie_tracker.authentication_feature.data.repository

import android.content.Context
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.data.mapper.toRegisterGoogleRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.AuthApiService
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleLoginRequest
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle
import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.mapper.toUser
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val authManager: AuthManager,
    private val userManager: UserManager,
    @ApplicationContext private val context: Context,
) : AuthRepository {

    private val jwtToken get() = authManager.token

    override suspend fun registerGoogle(createUser: CreateUserGoogle): AppResponse<Unit> {
        return when (val response =
            authApiService.registerGoogle(createUser.toRegisterGoogleRequest())) {
            is ApiResponse.Success -> {
                val user = response.body.user.toUser()
                val token = response.body.token
                userManager.setUser(user)
                authManager.setToken(token)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (response.code) {
                    HttpStatusCode.Conflict.value -> {
                        AppResponse.failed(
                            appException = AppException.GeneralException,
                            message = context.getString(R.string.error_user_already_exists)
                        )
                    }

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

    override suspend fun loginGoogle(idToken: String): AppResponse<Unit> {
        return when (val response = authApiService.loginGoogle(GoogleLoginRequest(idToken))) {
            is ApiResponse.Success -> {
                val user = response.body.user.toUser()
                val token = response.body.token
                userManager.setUser(user)
                authManager.setToken(token)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (response.code) {
                    HttpStatusCode.Unauthorized.value -> {
                        AppResponse.failed(
                            appException = AppException.UnauthorizedException,
                            message = context.getString(R.string.error_google_unauthorized)
                        )
                    }

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

    override suspend fun isAuthenticated(): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        val storedUser = userManager.getUser()
        return when (val response = authApiService.authenticate(token = token)) {
            is ApiResponse.Success -> {
                val user = response.body.toUser()
                userManager.setUser(user)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (response.code) {
                    HttpStatusCode.Unauthorized.value -> {
                        userManager.setUser(null)
                        authManager.setToken(null)
                        AppResponse.failed(
                            appException = AppException.UnauthorizedException,
                            message = context.getString(R.string.error_google_unauthorized)
                        )
                    }

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
                //Supporting offline-first mode
                if (storedUser.id != Constants.UNKNOWN_ID) {
                    return AppResponse.success(Unit)
                }
                AppResponse.failed(
                    appException = AppException.NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                //Supporting offline-first mode
                if (storedUser.id != Constants.UNKNOWN_ID) {
                    return AppResponse.success(Unit)
                }
                AppResponse.failed(
                    appException = AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }
}