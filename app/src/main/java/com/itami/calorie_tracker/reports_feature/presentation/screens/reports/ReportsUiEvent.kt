package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

sealed class ReportsUiEvent {

    data class ShowSnackbar(val message: String) : ReportsUiEvent()

    data object NavigateToProfile : ReportsUiEvent()

}