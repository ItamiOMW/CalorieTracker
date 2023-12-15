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
import com.itami.calorie_tracker.core.domain.exceptions.GeneralException
import com.itami.calorie_tracker.core.domain.exceptions.NetworkException
import com.itami.calorie_tracker.core.domain.exceptions.UnauthorizedException
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
        return when (val response = authApiService.registerGoogle(createUser.toRegisterGoogleRequest())) {
            is ApiResponse.Success -> {
                val user = response.body.user.toUser()
                val token = response.body.token
                userManager.setUser(user)
                authManager.setToken(token)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                when (response.code) {
                    HttpStatusCode.Conflict.value -> {
                        AppResponse.failed(
                            exception = GeneralException,
                            message = context.getString(R.string.error_user_already_exists)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            exception = GeneralException,
                            message = response.errorBody?.errorMessage
                        )
                    }
                }
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(
                    exception = NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    exception = GeneralException,
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

            is ApiResponse.Error.HttpError -> {
                when (response.code) {
                    HttpStatusCode.Unauthorized.value -> {
                        AppResponse.failed(
                            exception = UnauthorizedException,
                            message = context.getString(R.string.error_google_unauthorized)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            exception = GeneralException,
                            message = response.errorBody?.errorMessage
                        )
                    }
                }
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(
                    exception = NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    exception = GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun isAuthenticated(): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        val storedUser = userManager.getUser()
        return when (val response = authApiService.authenticate(token = token)) {
            is ApiResponse.Success -> {
                val user = response.body.toUser()
                userManager.setUser(user)
                return AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                when (response.code) {
                    HttpStatusCode.Unauthorized.value -> {
                        userManager.setUser(null)
                        authManager.setToken(null)
                        AppResponse.failed(
                            exception = UnauthorizedException,
                            message = context.getString(R.string.error_google_unauthorized)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            exception = GeneralException,
                            message = response.errorBody?.errorMessage
                        )
                    }
                }
            }

            is ApiResponse.Error.NetworkError -> {
                //Supporting offline-first mode
                if (storedUser.id != Constants.UNKNOWN_ID) {
                    return AppResponse.success(Unit)
                }
                AppResponse.failed(
                    exception = NetworkException,
                    message = context.getString(R.string.error_network)
                )
            }

            is ApiResponse.Error.SerializationError -> {
                //Supporting offline-first mode
                if (storedUser.id != Constants.UNKNOWN_ID) {
                    return AppResponse.success(Unit)
                }
                AppResponse.failed(
                    exception = GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }
}