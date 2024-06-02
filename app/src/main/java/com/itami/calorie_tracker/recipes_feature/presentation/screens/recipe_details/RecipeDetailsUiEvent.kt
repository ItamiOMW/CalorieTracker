package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details

sealed class RecipeDetailsUiEvent {

    data object NavigateBack : RecipeDetailsUiEvent()

}