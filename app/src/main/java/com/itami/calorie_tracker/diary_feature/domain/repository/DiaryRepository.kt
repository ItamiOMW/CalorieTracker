package com.itami.calorie_tracker.diary_feature.domain.repository

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedWater
import com.itami.calorie_tracker.diary_feature.domain.model.CreateMeal
import com.itami.calorie_tracker.diary_feature.domain.model.Food
import com.itami.calorie_tracker.diary_feature.domain.model.Meal
import com.itami.calorie_tracker.diary_feature.domain.model.UpdateMeal
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {

    fun getMeals(date: String): Flow<List<Meal>>

    fun getMeal(mealId: Int): Flow<Meal?>

    fun getConsumedWater(date: String): Flow<ConsumedWater?>

    suspend fun searchFood(query: String, page: Int, pageSize: Int): AppResponse<List<Food>>

    suspend fun loadMealsWithWater(date: String): AppResponse<Unit>

    suspend fun addConsumedWater(waterMl: Int, date: String): AppResponse<Unit>

    suspend fun removeConsumedWater(waterMl: Int, date: String): AppResponse<Unit>

    suspend fun createMeal(createMeal: CreateMeal): AppResponse<Unit>

    suspend fun updateMeal(mealId: Int, updateMeal: UpdateMeal): AppResponse<Unit>

    suspend fun deleteMeal(mealId: Int): AppResponse<Unit>

}