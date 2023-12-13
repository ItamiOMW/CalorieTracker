package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import com.itami.calorie_tracker.diary_feature.domain.model.Food

sealed class SearchFoodEvent {

    data class SearchQueryChange(val newValue: String): SearchFoodEvent()

    data class SetSelectedFood(val food: Food?): SearchFoodEvent()

    data object ClearSearchQuery: SearchFoodEvent()

    data object LoadNextPage: SearchFoodEvent()

    data object Refresh: SearchFoodEvent()

}
