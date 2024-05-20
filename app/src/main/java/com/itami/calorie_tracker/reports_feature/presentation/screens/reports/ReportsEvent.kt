package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi

sealed class ReportsEvent {

    data object ReloadWeights: ReportsEvent()

    data class ChangeWeightUnit(val weightUnit: WeightUnit): ReportsEvent()

    data class ShowAddWeightDialog(val show: Boolean): ReportsEvent()

    data class ShowEditWeightDialog(val weightToEdit: WeightUi?): ReportsEvent()

    data class AddWeight(val weightGrams: Int): ReportsEvent()

    data class EditWeight(val weightGrams: Int, val weightId: Int): ReportsEvent()

}