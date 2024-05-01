package com.itami.calorie_tracker.recipes_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe
import com.itami.calorie_tracker.recipes_feature.domain.repository.RecipesRepository

class GetRecipeByIdUseCase(private val recipesRepository: RecipesRepository) {

    suspend operator fun invoke(recipeId: Int): AppResponse<Recipe> {
        return recipesRepository.getRecipeById(recipeId = recipeId)
    }

}