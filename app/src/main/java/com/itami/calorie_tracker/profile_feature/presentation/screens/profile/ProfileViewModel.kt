package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appSettingsManager: AppSettingsManager,
    private val userManager: UserManager,
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        getUser()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.ChangeTheme -> {
                changeTheme(action.theme)
            }
        }
    }

    private fun changeTheme(theme: Theme) {
        viewModelScope.launch {
            appSettingsManager.changeTheme(theme)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userManager.user.collectLatest { user ->
                state = state.copy(user = user)
            }
        }
    }

}