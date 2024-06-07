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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appSettingsManager: AppSettingsManager,
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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

            is ProfileAction.AboutAppClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToAbout)
            }

            is ProfileAction.CalorieIntakeClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToCalorieIntake)
            }

            is ProfileAction.ContactUsClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToContactsUs)
            }

            is ProfileAction.MyInfoClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToAbout)
            }

            is ProfileAction.SettingsClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToSettings)
            }

            is ProfileAction.WaterIntakeClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToWaterIntake)
            }

            is ProfileAction.NavigateBackClick -> {
                sendUiEvent(ProfileUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(event: ProfileUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
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