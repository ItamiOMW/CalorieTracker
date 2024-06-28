package com.itami.calorie_tracker.settings_feature.presentation.screens.account

import android.net.Uri
import com.itami.calorie_tracker.core.domain.model.User

data class AccountState(
    val user: User = User.DEFAULT,
    val newProfilePictureUri: Uri? = null,
    val newName: String? = null,
    val showNameSheetContent: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
    val isLoading: Boolean = false,
)
