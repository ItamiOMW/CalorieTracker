package com.itami.calorie_tracker.core.utils

fun Int.mlToFormattedLiters(): String {
    val waterLiters = this / 1000f
    return "%.1f".format(waterLiters)
}