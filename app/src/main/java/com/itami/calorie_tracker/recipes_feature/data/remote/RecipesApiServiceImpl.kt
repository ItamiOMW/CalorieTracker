package com.itami.calorie_tracker.recipes_feature.data.remote

import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.safeRequest
import com.itami.calorie_tracker.recipes_feature.data.remote.response.RecipeResponse
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

class RecipesApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : RecipesApiService {

    companion object {

        private const val RECIPES = "/api/v1/recipes"

        private const val PAGE_PARAM = "page"
        private const val PAGE_SIZE_PARAM = "pageSize"
        private const val QUERY_PARAM = "query"
        private const val CALORIES_FILTERS_PARAM = "caloriesFilters"
        private const val TIME_FILTERS_PARAM = "timeFilters"
    }

    override suspend fun getRecipes(
        token: String,
        query: String,
        page: Int,
        pageSize: Int,
        timeFilters: List<TimeFilter>,
        caloriesFilters: List<CaloriesFilter>,
    ): ApiResponse<List<RecipeResponse>, ErrorResponse> {
        return httpClient.safeRequest {
            url(RECIPES)
            method = HttpMethod.Get
            bearerAuth(token)
            parameter(QUERY_PARAM, query)
            parameter(PAGE_PARAM, page)
            parameter(PAGE_SIZE_PARAM, pageSize)
            if (timeFilters.isNotEmpty()) {
                parameter(TIME_FILTERS_PARAM, timeFilters.joinToString(","))
            }
            if (caloriesFilters.isNotEmpty()) {
                parameter(CALORIES_FILTERS_PARAM, caloriesFilters.joinToString(","))
            }
        }
    }

    override suspend fun getRecipeById(
        token: String,
        id: Int,
    ): ApiResponse<RecipeResponse, ErrorResponse> {
        return httpClient.safeRequest {
            url("$RECIPES/$id")
            method = HttpMethod.Get
            bearerAuth(token)
        }
    }

}