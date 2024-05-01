package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details

import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe

data class RecipeDetailState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
