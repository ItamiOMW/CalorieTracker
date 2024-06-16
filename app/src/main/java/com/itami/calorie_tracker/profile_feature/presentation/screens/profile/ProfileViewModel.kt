package com.itami.calorie_tracker.profile_feature.presentation.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.LogoutUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
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
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ProfileState())
        private set

    init {
        getUser()
        getWeightUnit()
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

            is ProfileAction.MeClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToMyInfo)
            }

            is ProfileAction.SettingsClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToSettings)
            }

            is ProfileAction.WeightUnitClick -> {
                state = state.copy(showWeightUnitDialog = true)
            }

            is ProfileAction.SaveWeightUnit -> {
                state = state.copy(showWeightUnitDialog = false)
                saveWeightUnit(action.weightUnit)
            }

            is ProfileAction.DismissWeightUnitDialog -> {
                state = state.copy(showWeightUnitDialog = false)
            }

            is ProfileAction.NavigateBackClick -> {
                sendUiEvent(ProfileUiEvent.NavigateBack)
            }

            is ProfileAction.LogoutClick -> {
                state = state.copy(showLogoutDialog = true)
            }

            is ProfileAction.ConfirmLogout -> {
                state = state.copy(showLogoutDialog = false)
                logout()
            }

            is ProfileAction.DenyLogout -> {
                state = state.copy(showLogoutDialog = false)
            }
        }
    }

    private fun sendUiEvent(event: ProfileUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun logout() {
        state = state.copy(isLoggingOut = true)
        viewModelScope.launch {
            when (val response = logoutUseCase()) {
                is AppResponse.Success -> {
                    sendUiEvent(ProfileUiEvent.LogoutSuccess)
                }

                is AppResponse.Failed -> {
                    Unit
                }
            }
            state = state.copy(isLoggingOut = false)
        }
    }

    private fun changeTheme(theme: Theme) {
        viewModelScope.launch {
            appSettingsManager.changeTheme(theme)
        }
    }

    private fun saveWeightUnit(weightUnit: WeightUnit) {
        viewModelScope.launch {
            appSettingsManager.changeWeightUnit(weightUnit)
        }
    }

    private fun getWeightUnit() {
        viewModelScope.launch {
            appSettingsManager.weightUnit.collect { unit ->
                state = state.copy(weightUnit = unit)
            }
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