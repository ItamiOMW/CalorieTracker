package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes

sealed class RecipeDetailsUiEvent {

    data object NavigateBack : RecipeDetailsUiEvent()

}