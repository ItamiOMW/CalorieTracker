package com.itami.calorie_tracker.profile_feature.data.repository

import android.content.Context
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.profile_feature.data.remote.FeedbackApiService
import com.itami.calorie_tracker.profile_feature.data.remote.request.FeedbackMessageRequest
import com.itami.calorie_tracker.profile_feature.domain.repository.FeedbackRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val authManager: AuthManager,
    private val feedbackApiService: FeedbackApiService,
    @ApplicationContext private val context: Context,
) : FeedbackRepository {

    private val jwtToken get() = authManager.token

    override suspend fun sendFeedback(message: String): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)

        return when (val response = feedbackApiService.sendFeedbackMessage(
            token = token,
            feedbackMessageRequest = FeedbackMessageRequest(message)
        )) {
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