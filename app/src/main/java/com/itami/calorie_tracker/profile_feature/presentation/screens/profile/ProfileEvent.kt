package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import com.itami.calorie_tracker.core.domain.model.Theme

sealed class ProfileEvent {

    data class ChangeTheme(val theme: Theme): ProfileEvent()

}
