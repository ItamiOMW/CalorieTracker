package com.itami.calorie_tracker.settings_feature.presentation.screens.language

import java.util.Locale

sealed class LanguageAction {

    data object NavigateBackClick : LanguageAction()

    data class LanguageClick(val locale: Locale) : LanguageAction()

}