package com.itami.calorie_tracker.core.utils

import android.content.Context
import android.content.Intent
import com.itami.calorie_tracker.R

fun safeStartActivity(
    context: Context,
    intent: Intent,
    onException: (cause: String) -> Unit,
) {
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        onException(context.getString(R.string.error_app_not_found))
    }
}