package com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal

sealed class NewMealUiEvent {

    data class ShowSnackbar(val message: String): NewMealUiEvent()

    data object MealSaved: NewMealUiEvent()

    data object NavigateBack: NewMealUiEvent()

    data object NavigateToSearchFood: NewMealUiEvent()

}
