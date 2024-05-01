package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes

import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter

data class RecipesState(
    val user: User = User.DEFAULT,
    val recipes: List<Recipe> = emptyList(),
    val messageError: String? = null,
    val searchQuery: String = "",
    val page: Int = 1,
    val isLoadingNextRecipes: Boolean = false,
    val isRefreshingRecipes: Boolean = false,
    val endReached: Boolean = false,
    val timeFilters: List<TimeFilter> = emptyList(),
    val caloriesFilters: List<CaloriesFilter> = emptyList(),
    val showFilterOverlay: Boolean = false,
)
