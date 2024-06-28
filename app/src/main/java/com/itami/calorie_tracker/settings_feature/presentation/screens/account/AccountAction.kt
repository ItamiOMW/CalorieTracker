package com.itami.calorie_tracker.settings_feature.presentation.screens.account

import android.net.Uri

sealed class AccountAction {

    data class ChangeName(val newName: String) : AccountAction()

    data class ChangeProfilePicture(val uri: Uri) : AccountAction()

    data object NameClick : AccountAction()

    data class ChangePictureUri(val newUri: Uri) : AccountAction()

    data object LogoutClick : AccountAction()

    data object DeleteAccountClick : AccountAction()

    data object SaveClick : AccountAction()

    data object ChangePasswordClick : AccountAction()

    data object NavigateBackClick : AccountAction()

    data object DismissBottomSheet : AccountAction()

    data object ConfirmLogout : AccountAction()

    data object DenyLogout : AccountAction()

    data object ConfirmDeleteAccount : AccountAction()

    data object DenyDeleteAccount : AccountAction()

}