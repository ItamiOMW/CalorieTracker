package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.Food

data class SearchFoodState(
    val searchQuery: String = "",
    val foods: List<Food> = emptyList(),
    val foodsPage: Int = 1,
    val endReached: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val isRefreshing: Boolean = false,
    val selectedFood: ConsumedFood? = null,
)
