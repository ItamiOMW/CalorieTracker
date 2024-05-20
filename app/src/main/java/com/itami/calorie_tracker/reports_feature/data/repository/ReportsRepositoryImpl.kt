package com.itami.calorie_tracker.reports_feature.data.repository

import android.content.Context
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.reports_feature.data.mapper.toWeight
import com.itami.calorie_tracker.reports_feature.data.remote.ReportsApiService
import com.itami.calorie_tracker.reports_feature.data.remote.dto.request.AddWeightRequest
import com.itami.calorie_tracker.reports_feature.data.remote.dto.request.EditWeightRequest
import com.itami.calorie_tracker.reports_feature.domain.model.Weight
import com.itami.calorie_tracker.reports_feature.domain.repository.ReportsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.http.HttpStatusCode
import io.ktor.util.encodeBase64
import javax.inject.Inject

class ReportsRepositoryImpl @Inject constructor(
    private val reportsApiService: ReportsApiService,
    private val authManager: AuthManager,
    @ApplicationContext private val context: Context,
) : ReportsRepository {

    private val jwtToken get() = authManager.token

    override suspend fun getWeights(): AppResponse<List<Weight>> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        return when (val apiResponse = reportsApiService.getWeights(token = token)) {
            is ApiResponse.Success -> {
                val weights = apiResponse.body.map { it.toWeight() }
                AppResponse.success(weights)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (apiResponse.code) {
                    HttpStatusCode.TooManyRequests.value -> {
                        AppResponse.failed(
                            appException = AppException.TooManyRequestsException,
                            message = context.getString(R.string.error_too_many_requests)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            AppException.GeneralException,
                            message = context.getString(R.string.error_unknown)
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
                AppResponse.failed(AppException.NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun addWeight(datetime: String, weightGrams: Int): AppResponse<Weight> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        return when (val apiResponse = reportsApiService.addWeight(
            token = token,
            addWeightRequest = AddWeightRequest(datetime.encodeBase64(), weightGrams)
        )) {
            is ApiResponse.Success -> {
                val weight = apiResponse.body.toWeight()
                AppResponse.success(weight)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (apiResponse.code) {
                    HttpStatusCode.TooManyRequests.value -> {
                        AppResponse.failed(
                            appException = AppException.TooManyRequestsException,
                            message = context.getString(R.string.error_too_many_requests)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            AppException.GeneralException,
                            message = context.getString(R.string.error_unknown)
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
                AppResponse.failed(AppException.NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun editWeight(weightId: Int, weightGrams: Int): AppResponse<Weight> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        return when (val apiResponse = reportsApiService.editWeight(
            token = token,
            editWeightRequest = EditWeightRequest(weightGrams),
            weightId = weightId
        )) {
            is ApiResponse.Success -> {
                val weight = apiResponse.body.toWeight()
                AppResponse.success(weight)
            }

            is ApiResponse.Error.HttpClientError -> {
                when (apiResponse.code) {
                    HttpStatusCode.TooManyRequests.value -> {
                        AppResponse.failed(
                            appException = AppException.TooManyRequestsException,
                            message = context.getString(R.string.error_too_many_requests)
                        )
                    }

                    else -> {
                        AppResponse.failed(
                            AppException.GeneralException,
                            message = context.getString(R.string.error_unknown)
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
                AppResponse.failed(AppException.NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    AppException.GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }
}