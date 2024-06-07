package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import com.itami.calorie_tracker.core.domain.model.Theme

sealed class ProfileAction {

    data class ChangeTheme(val theme: Theme) : ProfileAction()

    data object MyInfoClick : ProfileAction()

    data object CalorieIntakeClick : ProfileAction()

    data object WaterIntakeClick : ProfileAction()

    data object ContactUsClick : ProfileAction()

    data object AboutAppClick : ProfileAction()

    data object SettingsClick : ProfileAction()

    data object NavigateBackClick : ProfileAction()

}
