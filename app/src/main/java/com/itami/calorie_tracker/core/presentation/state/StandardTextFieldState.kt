package com.itami.calorie_tracker.core.presentation.state

data class StandardTextFieldState(
    val text: String = "",
    val errorMessage: String? = null
)