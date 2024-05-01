package com.itami.calorie_tracker.recipes_feature.data.repository

import android.content.Context
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.recipes_feature.data.mapper.toRecipe
import com.itami.calorie_tracker.recipes_feature.data.remote.RecipesApiService
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter
import com.itami.calorie_tracker.recipes_feature.domain.repository.RecipesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesApiService: RecipesApiService,
    private val authManager: AuthManager,
    @ApplicationContext private val context: Context,
) : RecipesRepository {

    private val jwtToken get() = authManager.token

    override suspend fun getRecipes(
        query: String,
        page: Int,
        pageSize: Int,
        timeFilters: List<TimeFilter>,
        caloriesFilters: List<CaloriesFilter>,
    ): AppResponse<List<Recipe>> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        return when (val response = recipesApiService.getRecipes(
            token = token,
            query = query,
            page = page,
            pageSize = pageSize,
            timeFilters = timeFilters,
            caloriesFilters = caloriesFilters
        )) {
            is ApiResponse.Success -> {
                val recipes = response.body.map { it.toRecipe() }
                return AppResponse.success(recipes)
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

    override suspend fun getRecipeById(recipeId: Int): AppResponse<Recipe> {
        val token = jwtToken ?: return AppResponse.failed(AppException.UnauthorizedException)
        return when (val response = recipesApiService.getRecipeById(token, recipeId)) {
            is ApiResponse.Success -> {
                val recipe = response.body.toRecipe()
                return AppResponse.success(recipe)
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