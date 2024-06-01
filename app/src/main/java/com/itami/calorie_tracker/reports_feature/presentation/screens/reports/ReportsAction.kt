package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi

sealed class ReportsAction {

    data object ReloadWeights: ReportsAction()

    data class ChangeWeightUnit(val weightUnit: WeightUnit): ReportsAction()

    data class ShowAddWeightDialog(val show: Boolean): ReportsAction()

    data class ShowEditWeightDialog(val weightToEdit: WeightUi?): ReportsAction()

    data class AddWeight(val weightGrams: Int): ReportsAction()

    data class EditWeight(val weightGrams: Int, val weightId: Int): ReportsAction()

}