package com.itami.calorie_tracker.core.domain.model

enum class Language(val isoValue: String) {
    English("en"),
    Russian("ru"),
    French("fr"),
    German("de"),
    Spanish("es"),
}

fun String.isoToLanguage(): Language {
    return when(this) {
        Language.English.isoValue -> Language.English
        Language.Russian.isoValue -> Language.Russian
        Language.French.isoValue -> Language.French
        Language.German.isoValue -> Language.German
        Language.Spanish.isoValue -> Language.Spanish
        else -> { Language.English }
    }
}