package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info

sealed class UserInfoUiEvent {

    data object UserInfoSaved: UserInfoUiEvent()

    data class ShowSnackbar(val message: String) : UserInfoUiEvent()

    data object NavigateBack : UserInfoUiEvent()

}