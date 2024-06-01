package com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email

import android.net.Uri

sealed class RegisterEmailAction {

    data class PictureUriChange(val uri: Uri) : RegisterEmailAction()

    data class NameInputChange(val newValue: String) : RegisterEmailAction()

    data class EmailInputChange(val newValue: String) : RegisterEmailAction()

    data class PasswordInputChange(val newValue: String) : RegisterEmailAction()

    data class PasswordVisibilityChange(val isVisible: Boolean) : RegisterEmailAction()

    data object Register : RegisterEmailAction()

}