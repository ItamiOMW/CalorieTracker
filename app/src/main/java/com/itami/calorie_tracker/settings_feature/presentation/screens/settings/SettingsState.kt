package com.itami.calorie_tracker.settings_feature.presentation.screens.settings

import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.utils.Constants

data class SettingsState(
    val user: User = User.DEFAULT,
    val weightUnit: WeightUnit = WeightUnit.KILOGRAM,
    val heightUnit: HeightUnit = HeightUnit.METER,
    val waterServingSizeMl: Int = Constants.DEFAULT_WATER_SERVING_ML,
    val theme: Theme = Theme.SYSTEM_THEME,
    val waterIntakeEnabled: Boolean = true,
    val subscriptionEnabled: Boolean = false,
    val dialogContent: SettingsScreenDialogContent? = null,
)

enum class SettingsScreenDialogContent {
    WEIGHT_UNIT,
    HEIGHT_UNIT,
    WATER_SERVING_SIZE,
    THEME,
    WATER_TRACKER,
}
