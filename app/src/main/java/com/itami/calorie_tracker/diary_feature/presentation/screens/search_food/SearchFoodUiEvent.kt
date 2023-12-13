package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

sealed class SearchFoodUiEvent {

    data class ShowSnackbar(val message: String): SearchFoodUiEvent()

}
