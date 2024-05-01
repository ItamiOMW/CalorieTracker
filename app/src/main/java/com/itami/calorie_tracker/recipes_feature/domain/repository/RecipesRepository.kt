package com.itami.calorie_tracker.recipes_feature.domain.repository

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter

interface RecipesRepository {

    suspend fun getRecipes(
        query: String,
        page: Int,
        pageSize: Int,
        timeFilters: List<TimeFilter>,
        caloriesFilters: List<CaloriesFilter>,
    ): AppResponse<List<Recipe>>

    suspend fun getRecipeById(recipeId: Int): AppResponse<Recipe>

}