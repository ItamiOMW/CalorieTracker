package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

sealed class DiaryUiEvent {

    data class ShowSnackbar(val message: String): DiaryUiEvent()

    data object NavigateToProfile : DiaryUiEvent()

    data class NavigateToNewMeal(val datetime: String) : DiaryUiEvent()

    data class NavigateToMeal(val mealId: Int) : DiaryUiEvent()

}
