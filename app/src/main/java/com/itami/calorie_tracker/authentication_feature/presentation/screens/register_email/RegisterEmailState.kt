package com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email

import android.net.Uri
import com.itami.calorie_tracker.core.presentation.state.PasswordTextFieldState
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState

data class RegisterEmailState(
    val nameState: StandardTextFieldState = StandardTextFieldState(),
    val emailState: StandardTextFieldState = StandardTextFieldState(),
    val passwordState: PasswordTextFieldState = PasswordTextFieldState(),
    val profilePictureUri: Uri? = null,
    val isLoading: Boolean = false,
)
