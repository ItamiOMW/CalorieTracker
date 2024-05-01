package com.itami.calorie_tracker.recipes_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter
import com.itami.calorie_tracker.recipes_feature.domain.repository.RecipesRepository

class GetRecipesUseCase(private val recipesRepository: RecipesRepository) {

    suspend operator fun invoke(
        query: String = "",
        page: Int = 1,
        pageSize: Int = 10,
        timeFilters: List<TimeFilter> = emptyList(),
        caloriesFilters: List<CaloriesFilter> = emptyList(),
    ): AppResponse<List<Recipe>> {
        return recipesRepository.getRecipes(
            query = query,
            page = page,
            pageSize = pageSize,
            timeFilters = timeFilters,
            caloriesFilters = caloriesFilters
        )
    }

}