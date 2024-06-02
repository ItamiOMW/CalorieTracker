package com.itami.calorie_tracker.diary_feature.presentation.screens.meal

sealed class MealUiEvent {

    data class ShowSnackbar(val message: String): MealUiEvent()

    data object MealSaved: MealUiEvent()

    data object NavigateBack: MealUiEvent()

    data object NavigateToSearchFood: MealUiEvent()

}
