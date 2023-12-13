package com.itami.calorie_tracker.diary_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.safeRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.AddConsumedWaterRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.CreateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.RemoveConsumedWaterRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.UpdateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.response.ConsumedWaterResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.FoodResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.MealResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.MealsWithConsumedWaterResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.retry
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.util.encodeBase64
import javax.inject.Inject

class DiaryApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : DiaryApiService {

    companion object {

        private const val FOODS = "/api/v1/foods"

        private const val SUMMARY = "/api/v1/summary"

        private const val MEALS = "/api/v1/meals"

        private const val WATER = "/api/v1/water"
        private const val WATER_ADD = "$WATER/add"
        private const val WATER_REMOVE = "$WATER/remove"

        private const val ENCODED_DATETIME_PARAM = "encoded_datetime"
        private const val PAGE_PARAM = "page"
        private const val PAGE_SIZE_PARAM = "pageSize"
        private const val QUERY_PARAM = "query"
    }

    override suspend fun searchFoods(
        token: String,
        query: String,
        page: Int,
        pageSize: Int,
    ): ApiResponse<List<FoodResponse>, ErrorResponse> {
        return httpClient.safeRequest {
            url(FOODS)
            method = HttpMethod.Get
            bearerAuth(token)
            parameter(QUERY_PARAM, query)
            parameter(PAGE_PARAM, page)
            parameter(PAGE_SIZE_PARAM, pageSize)
        }
    }

    override suspend fun getMealsWithWaterIntake(
        token: String,
        date: String,
    ): ApiResponse<MealsWithConsumedWaterResponse, ErrorResponse> {
        val encodedDate = date.encodeBase64()
        return httpClient.safeRequest {
            url(SUMMARY)
            method = HttpMethod.Get
            bearerAuth(token)
            parameter(ENCODED_DATETIME_PARAM, encodedDate)
        }
    }

    override suspend fun addConsumedWater(
        token: String,
        addConsumedWaterRequest: AddConsumedWaterRequest,
    ): ApiResponse<ConsumedWaterResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(WATER_ADD)
            method = HttpMethod.Put
            bearerAuth(token)
            setBody(addConsumedWaterRequest)
            retry { noRetry() }
        }
    }

    override suspend fun removeConsumedWater(
        token: String,
        removeConsumedWaterRequest: RemoveConsumedWaterRequest,
    ): ApiResponse<ConsumedWaterResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(WATER_REMOVE)
            method = HttpMethod.Put
            bearerAuth(token)
            setBody(removeConsumedWaterRequest)
            retry { noRetry() }
        }
    }

    override suspend fun getMeal(
        token: String,
        mealId: Int,
    ): ApiResponse<MealResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url("$MEALS/$mealId")
            method = HttpMethod.Get
            bearerAuth(token)
        }
    }

    override suspend fun createMeal(
        token: String,
        createMealRequest: CreateMealRequest,
    ): ApiResponse<MealResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url(MEALS)
            method = HttpMethod.Post
            bearerAuth(token)
            setBody(createMealRequest)
        }
    }

    override suspend fun updateMeal(
        token: String,
        mealId: Int,
        updateMealRequest: UpdateMealRequest,
    ): ApiResponse<MealResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url("$MEALS/$mealId")
            method = HttpMethod.Put
            bearerAuth(token)
            setBody(updateMealRequest)
        }
    }

    override suspend fun deleteMeal(
        token: String,
        mealId: Int,
    ): ApiResponse<Unit, ErrorResponse> {
        return httpClient.safeRequest {
            url("$MEALS/$mealId")
            method = HttpMethod.Delete
            bearerAuth(token)
        }
    }
}