package com.itami.calorie_tracker.recipes_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.recipes_feature.data.remote.response.RecipeResponse
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter

interface RecipesApiService {

    suspend fun getRecipes(
        token: String,
        query: String,
        page: Int = 1,
        pageSize: Int = 10,
        timeFilters: List<TimeFilter>,
        caloriesFilters: List<CaloriesFilter>,
    ): ApiResponse<List<RecipeResponse>, ErrorResponse>

    suspend fun getRecipeById(
        token: String,
        id: Int,
    ): ApiResponse<RecipeResponse, ErrorResponse>

}