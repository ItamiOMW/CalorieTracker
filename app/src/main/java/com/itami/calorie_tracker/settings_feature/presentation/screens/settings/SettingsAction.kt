package com.itami.calorie_tracker.settings_feature.presentation.screens.settings

import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightUnit

sealed class SettingsAction {

    data object AccountFieldClick : SettingsAction()

    data object WeightUnitFieldClick : SettingsAction()

    data object HeightUnitFieldClick : SettingsAction()

    data object WaterServingSizeFieldClick : SettingsAction()

    data object LanguageFieldClick : SettingsAction()

    data object ThemeFieldClick : SettingsAction()

    data object WaterTrackerFieldClick : SettingsAction()

    data object SubscriptionFieldClick : SettingsAction()

    data class ChangeHeightUnit(val heightUnit: HeightUnit): SettingsAction()

    data class ChangeWeightUnit(val weightUnit: WeightUnit): SettingsAction()

    data class ChangeTheme(val theme: Theme): SettingsAction()

    data class ChangeWaterServingSize(val waterMl: Int): SettingsAction()

    data class ChangeWaterTrackerEnabledState(val enabled: Boolean): SettingsAction()

    data object DismissDialog : SettingsAction()

    data object NavigateBackClick : SettingsAction()

}