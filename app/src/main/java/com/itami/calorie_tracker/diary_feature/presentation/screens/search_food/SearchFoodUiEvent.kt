package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood

sealed class SearchFoodUiEvent {

    data class ShowSnackbar(val message: String): SearchFoodUiEvent()

    data class NavigateBackWithFood(val consumedFood: ConsumedFood) : SearchFoodUiEvent()

    data object NavigateBack : SearchFoodUiEvent()

}
