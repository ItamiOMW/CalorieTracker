package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes

sealed class RecipesUiEvent {

    data class ShowSnackbar(val message: String): RecipesUiEvent()

    data class NavigateToRecipeDetails(val recipeId: Int): RecipesUiEvent()

    data object NavigateToProfile: RecipesUiEvent()

}