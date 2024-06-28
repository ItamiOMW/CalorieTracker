package com.itami.calorie_tracker.core.utils

import android.content.Context

object LanguageUtil {

    fun getCurrentLanguage(context: Context): String {
        return context.resources.configuration.locales.get(0).language
    }

}