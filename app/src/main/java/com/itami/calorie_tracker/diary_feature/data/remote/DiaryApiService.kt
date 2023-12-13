package com.itami.calorie_tracker.diary_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.diary_feature.data.remote.request.AddConsumedWaterRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.CreateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.RemoveConsumedWaterRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.UpdateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.response.ConsumedWaterResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.FoodResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.MealResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.MealsWithConsumedWaterResponse

interface DiaryApiService {

    suspend fun searchFoods(
        token: String,
        query: String,
        page: Int = 1,
        pageSize: Int = 20,
    ): ApiResponse<List<FoodResponse>, ErrorResponse>

    suspend fun getMealsWithWaterIntake(
        token: String,
        date: String,
    ): ApiResponse<MealsWithConsumedWaterResponse, ErrorResponse>

    suspend fun addConsumedWater(
        token: String,
        addConsumedWaterRequest: AddConsumedWaterRequest,
    ): ApiResponse<ConsumedWaterResponse, ErrorResponse>

    suspend fun removeConsumedWater(
        token: String,
        removeConsumedWaterRequest: RemoveConsumedWaterRequest,
    ): ApiResponse<ConsumedWaterResponse, ErrorResponse>

    suspend fun getMeal(
        token: String,
        mealId: Int,
    ): ApiResponse<MealResponse, ErrorResponse>

    suspend fun createMeal(
        token: String,
        createMealRequest: CreateMealRequest,
    ): ApiResponse<MealResponse, ErrorResponse>

    suspend fun updateMeal(
        token: String,
        mealId: Int,
        updateMealRequest: UpdateMealRequest,
    ): ApiResponse<MealResponse, ErrorResponse>

    suspend fun deleteMeal(token: String, mealId: Int): ApiResponse<Unit, ErrorResponse>

}