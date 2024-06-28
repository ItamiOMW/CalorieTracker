package com.itami.calorie_tracker.settings_feature.presentation.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appSettingsManager: AppSettingsManager,
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiEvent = Channel<SettingsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SettingsState())
        private set

    init {
        getWeightUnit()
        getHeightUnit()
        getUser()
        getWaterServingSize()
        getWaterTrackerEnabledState()
        getTheme()
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.NavigateBackClick -> {
                sendUiEvent(SettingsUiEvent.NavigateBack)
            }

            is SettingsAction.HeightUnitFieldClick -> {
                state = state.copy(dialogContent = SettingsScreenDialogContent.HEIGHT_UNIT)
            }

            is SettingsAction.LanguageFieldClick -> {
                sendUiEvent(SettingsUiEvent.NavigateToLanguage)
            }

            is SettingsAction.SubscriptionFieldClick -> {
                sendUiEvent(SettingsUiEvent.NavigateToSubscription)
            }

            is SettingsAction.ThemeFieldClick -> {
                state = state.copy(dialogContent = SettingsScreenDialogContent.THEME)
            }

            is SettingsAction.AccountFieldClick -> {
                sendUiEvent(SettingsUiEvent.NavigateToAccount)
            }

            is SettingsAction.WaterTrackerFieldClick -> {
                state = state.copy(dialogContent = SettingsScreenDialogContent.WATER_TRACKER)
            }

            is SettingsAction.WaterServingSizeFieldClick -> {
                state = state.copy(dialogContent = SettingsScreenDialogContent.WATER_SERVING_SIZE)
            }

            is SettingsAction.WeightUnitFieldClick -> {
                state = state.copy(dialogContent = SettingsScreenDialogContent.WEIGHT_UNIT)
            }

            is SettingsAction.ChangeHeightUnit -> {
                state = state.copy(dialogContent = null)
                changeHeightUnit(action.heightUnit)
            }

            is SettingsAction.ChangeTheme -> {
                state = state.copy(dialogContent = null)
                changeTheme(action.theme)
            }

            is SettingsAction.ChangeWaterServingSize -> {
                state = state.copy(dialogContent = null)
                changeWaterServingSize(action.waterMl)
            }

            is SettingsAction.ChangeWaterTrackerEnabledState -> {
                state = state.copy(dialogContent = null)
                changeWaterTrackerEnabledState(action.enabled)
            }

            is SettingsAction.ChangeWeightUnit -> {
                state = state.copy(dialogContent = null)
                changeWeightUnit(action.weightUnit)
            }

            is SettingsAction.DismissDialog -> {
                state = state.copy(dialogContent = null)
            }
        }
    }

    private fun sendUiEvent(event: SettingsUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun changeHeightUnit(heightUnit: HeightUnit) {
        viewModelScope.launch {
            appSettingsManager.changeHeightUnit(heightUnit)
        }
    }


    private fun changeWeightUnit(weightUnit: WeightUnit) {
        viewModelScope.launch {
            appSettingsManager.changeWeightUnit(weightUnit)
        }
    }

    private fun changeWaterServingSize(waterMl: Int) {
        viewModelScope.launch {
            appSettingsManager.changeWaterServingSize(waterMl)
        }
    }

    private fun changeWaterTrackerEnabledState(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsManager.changeWaterTrackerEnabledState(enabled)
        }
    }

    private fun changeTheme(theme: Theme) {
        viewModelScope.launch {
            appSettingsManager.changeTheme(theme)
        }
    }

    private fun getWeightUnit() {
        viewModelScope.launch {
            appSettingsManager.weightUnit.collect { state = state.copy(weightUnit = it) }
        }
    }

    private fun getHeightUnit() {
        viewModelScope.launch {
            appSettingsManager.heightUnit.collect { state = state.copy(heightUnit = it) }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userManager.user.collect { state = state.copy(user = it) }
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            appSettingsManager.theme.collect { state = state.copy(theme = it) }
        }
    }

    private fun getWaterServingSize() {
        viewModelScope.launch {
            appSettingsManager.waterServingSize.collect { state = state.copy(waterServingSizeMl = it) }
        }
    }

    private fun getWaterTrackerEnabledState() {
        viewModelScope.launch {
            appSettingsManager.waterTrackerEnabled.collect { state = state.copy(waterIntakeEnabled = it) }
        }
    }

}