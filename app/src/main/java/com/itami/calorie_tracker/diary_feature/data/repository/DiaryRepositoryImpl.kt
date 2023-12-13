package com.itami.calorie_tracker.diary_feature.data.repository

import android.content.Context
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.domain.exceptions.GeneralException
import com.itami.calorie_tracker.core.domain.exceptions.NetworkException
import com.itami.calorie_tracker.core.domain.exceptions.UnauthorizedException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.diary_feature.data.local.dao.ConsumedFoodDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.ConsumedWaterDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.FoodDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.MealDao
import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedFoodEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.FoodEntity
import com.itami.calorie_tracker.diary_feature.data.mapper.toConsumedFoodEntity
import com.itami.calorie_tracker.diary_feature.data.mapper.toConsumedWater
import com.itami.calorie_tracker.diary_feature.data.mapper.toConsumedWaterEntity
import com.itami.calorie_tracker.diary_feature.data.mapper.toCreateMealRequest
import com.itami.calorie_tracker.diary_feature.data.mapper.toFood
import com.itami.calorie_tracker.diary_feature.data.mapper.toFoodEntity
import com.itami.calorie_tracker.diary_feature.data.mapper.toMeal
import com.itami.calorie_tracker.diary_feature.data.mapper.toMealEntity
import com.itami.calorie_tracker.diary_feature.data.mapper.toUpdateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.DiaryApiService
import com.itami.calorie_tracker.diary_feature.data.remote.request.AddConsumedWaterRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.RemoveConsumedWaterRequest
import com.itami.calorie_tracker.diary_feature.data.remote.response.MealResponse
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedWater
import com.itami.calorie_tracker.diary_feature.domain.model.CreateMeal
import com.itami.calorie_tracker.diary_feature.domain.model.Food
import com.itami.calorie_tracker.diary_feature.domain.model.Meal
import com.itami.calorie_tracker.diary_feature.domain.model.UpdateMeal
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZoneId
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryApiService: DiaryApiService,
    private val mealDao: MealDao,
    private val foodDao: FoodDao,
    private val consumedWaterDao: ConsumedWaterDao,
    private val consumedFoodDao: ConsumedFoodDao,
    private val authManager: AuthManager,
    @ApplicationContext private val context: Context,
) : DiaryRepository {

    private val jwtToken get() = authManager.token

    override fun getMeals(date: String): Flow<List<Meal>> {
        val utcDate = DateTimeUtil.changeDateTimeStrZone(date, ZoneId.of("UTC"))
        return mealDao.getMeals(utcDate = utcDate).map { list ->
            list.map { mealWithFoods -> mealWithFoods.toMeal() }
        }
    }

    override fun getMeal(mealId: Int): Flow<Meal?> {
        return mealDao.getMealById(id = mealId).map { it?.toMeal() }
    }

    override fun getConsumedWater(date: String): Flow<ConsumedWater?> {
        val utcDate = DateTimeUtil.changeDateTimeStrZone(date, ZoneId.of("UTC"))
        return consumedWaterDao.getConsumedWaterFlow(utcDate = utcDate)
            .map { it?.toConsumedWater() }
    }

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): AppResponse<List<Food>> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        return when (
            val response = diaryApiService.searchFoods(
                token = token,
                query = query,
                page = page,
                pageSize = pageSize
            )
        ) {
            is ApiResponse.Success -> {
                val foodsResponse = response.body
                val foods = foodsResponse.map { it.toFood() }
                AppResponse.success(foods)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun loadMealsWithWater(date: String): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        return when (val response =
            diaryApiService.getMealsWithWaterIntake(token = token, date = date)) {
            is ApiResponse.Success -> {
                val meals = response.body.meals
                insertMeals(meals)
                AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun addConsumedWater(waterMl: Int, date: String): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        val addConsumedWaterRequest = AddConsumedWaterRequest(date, waterMl)
        return when (val response =
            diaryApiService.addConsumedWater(token, addConsumedWaterRequest)) {
            is ApiResponse.Success -> {
                val consumedWater = response.body
                consumedWaterDao.insertConsumedWater(consumedWater.toConsumedWaterEntity())
                AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun removeConsumedWater(waterMl: Int, date: String): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        val removeConsumedWaterRequest = RemoveConsumedWaterRequest(date, waterMl)
        return when (val response =
            diaryApiService.removeConsumedWater(token, removeConsumedWaterRequest)) {
            is ApiResponse.Success -> {
                val consumedWater = response.body
                consumedWaterDao.updateConsumedWater(consumedWater.toConsumedWaterEntity())
                AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun createMeal(createMeal: CreateMeal): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        val createMealRequest = createMeal.toCreateMealRequest()
        return when (val response = diaryApiService.createMeal(token, createMealRequest)) {
            is ApiResponse.Success -> {
                val mealEntity = response.body
                insertMeals(listOf(mealEntity))
                AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun updateMeal(mealId: Int, updateMeal: UpdateMeal): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        val updateMealRequest = updateMeal.toUpdateMealRequest()
        return when (val response = diaryApiService.updateMeal(token, mealId, updateMealRequest)) {
            is ApiResponse.Success -> {
                val mealEntity = response.body
                insertMeals(listOf(mealEntity))
                AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    override suspend fun deleteMeal(mealId: Int): AppResponse<Unit> {
        val token = jwtToken ?: return AppResponse.failed(UnauthorizedException)
        return when (diaryApiService.deleteMeal(token, mealId)) {
            is ApiResponse.Success -> {
                mealDao.deleteMeal(mealId)
                AppResponse.success(Unit)
            }

            is ApiResponse.Error.HttpError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }

            is ApiResponse.Error.NetworkError -> {
                AppResponse.failed(NetworkException)
            }

            is ApiResponse.Error.SerializationError -> {
                AppResponse.failed(
                    GeneralException,
                    message = context.getString(R.string.error_unknown)
                )
            }
        }
    }

    private suspend fun insertMeals(meals: List<MealResponse>) {
        val mealEntities = meals.map { it.toMealEntity() }
        mealDao.insertMeals(mealEntities)

        val foodEntities = mutableListOf<FoodEntity>()
        meals.map { meal -> foodEntities.addAll(meal.consumedFoods.map { it.food.toFoodEntity() }) }
        foodDao.insertAll(foodEntities)

        val consumedFoodEntities = mutableListOf<ConsumedFoodEntity>()
        meals.map { meal ->
            consumedFoodEntities.addAll(meal.consumedFoods.map {
                it.toConsumedFoodEntity(
                    meal.id
                )
            })
        }
        consumedFoodDao.insertConsumedFoods(consumedFoodEntities)
    }
}