package com.itami.calorie_tracker.settings_feature.presentation.screens.language

import java.util.Locale

data class LanguageState(
    val locales: List<Locale> = listOf(
        Locale("en"),
        Locale("ru"),
        Locale("fr"),
        Locale("de"),
        Locale("es"),
    ),
    val selectedLocale: Locale = Locale.ENGLISH,
    val isLoading: Boolean = false,
)
