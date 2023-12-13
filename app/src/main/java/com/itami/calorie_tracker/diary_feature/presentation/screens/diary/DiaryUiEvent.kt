package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

sealed class DiaryUiEvent {

    data class ShowSnackbar(val message: String): DiaryUiEvent()

}
