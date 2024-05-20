package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi

data class ReportsState(
    val user: User = User.DEFAULT,
    val weightUnit: WeightUnit = WeightUnit.KILOGRAM,
    val weights: List<WeightUi> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingWeights: Boolean = false,
    val errorMessage: String? = null,
    val weightToEdit: WeightUi? = null,
    val showAddWeightDialog: Boolean = false,
)
