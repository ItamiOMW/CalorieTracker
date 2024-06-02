package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi

sealed class ReportsAction {

    data object ReloadWeights : ReportsAction()

    data class ChangeWeightUnit(val weightUnit: WeightUnit) : ReportsAction()

    data class AddWeight(val weightGrams: Int) : ReportsAction()

    data class EditWeight(val weightGrams: Int, val weightId: Int) : ReportsAction()

    data object ProfilePictureClick : ReportsAction()

    data object AddWeightClick : ReportsAction()

    data object DismissAddWeightDialog : ReportsAction()

    data class WeightClick(val weight: WeightUi) : ReportsAction()

    data object DismissEditWeightDialog : ReportsAction()

}