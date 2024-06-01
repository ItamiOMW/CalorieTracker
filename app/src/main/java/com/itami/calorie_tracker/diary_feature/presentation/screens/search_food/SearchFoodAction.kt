package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import com.itami.calorie_tracker.diary_feature.domain.model.Food

sealed class SearchFoodAction {

    data class SearchQueryChange(val newValue: String): SearchFoodAction()

    data class SetSelectedFood(val food: Food?): SearchFoodAction()

    data object ClearSearchQuery: SearchFoodAction()

    data object LoadNextPage: SearchFoodAction()

    data object Refresh: SearchFoodAction()

}
