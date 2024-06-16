package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightUnit

sealed class ProfileAction {

    data class ChangeTheme(val theme: Theme) : ProfileAction()

    data object MeClick : ProfileAction()

    data object CalorieIntakeClick : ProfileAction()

    data object WeightUnitClick : ProfileAction()

    data class SaveWeightUnit(val weightUnit: WeightUnit) : ProfileAction()

    data object DismissWeightUnitDialog: ProfileAction()

    data object ContactUsClick : ProfileAction()

    data object AboutAppClick : ProfileAction()

    data object SettingsClick : ProfileAction()

    data object LogoutClick : ProfileAction()

    data object ConfirmLogout : ProfileAction()

    data object DenyLogout : ProfileAction()

    data object NavigateBackClick : ProfileAction()

}
