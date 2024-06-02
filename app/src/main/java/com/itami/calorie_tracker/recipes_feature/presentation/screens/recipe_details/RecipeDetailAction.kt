package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details

sealed class RecipeDetailAction {

    data object RetryClick: RecipeDetailAction()

    data object NavigateBackClick: RecipeDetailAction()

}