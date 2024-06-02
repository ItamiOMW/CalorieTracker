package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.Food

sealed class SearchFoodAction {

    data class SearchQueryChange(val newValue: String): SearchFoodAction()

    data class FoodClick(val food: Food): SearchFoodAction()

    data object ClearSearchQueryClick: SearchFoodAction()

    data object LoadNextPage: SearchFoodAction()

    data object Refresh: SearchFoodAction()

    data object NavigateBackClick: SearchFoodAction()

    data class AddConsumedFood(val consumedFood: ConsumedFood): SearchFoodAction()

    data object DismissConsumedFoodDialog: SearchFoodAction()

}
