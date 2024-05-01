package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details

sealed class RecipeDetailEvent {

    data object Retry: RecipeDetailEvent()

}