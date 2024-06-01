package com.itami.calorie_tracker.core.presentation.state

data class PasswordTextFieldState(
    val text: String = "",
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null
)