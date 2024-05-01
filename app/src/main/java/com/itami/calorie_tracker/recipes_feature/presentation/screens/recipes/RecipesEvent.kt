package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes

import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter

sealed class RecipesEvent {

    data object Refresh : RecipesEvent()

    data object LoadNextRecipes : RecipesEvent()

    data class SearchQueryChange(val newValue: String) : RecipesEvent()

    data class ShowFilterOverlay(val show: Boolean) : RecipesEvent()

    data class UpdateFilters(
        val timeFilters: List<TimeFilter>,
        val caloriesFilters: List<CaloriesFilter>,
    ): RecipesEvent()

}