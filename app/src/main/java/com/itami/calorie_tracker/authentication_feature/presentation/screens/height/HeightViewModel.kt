package com.itami.calorie_tracker.authentication_feature.presentation.screens.height

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.InvalidHeightException
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.SetHeightUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val userManager: UserManager,
    private val setHeightUseCase: SetHeightUseCase,
    private val appSettingsManager: AppSettingsManager,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<HeightUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(HeightState())
        private set

    init {
        getHeight()
        getHeightUnit()
    }

    fun onEvent(event: HeightEvent) {
        when (event) {
            is HeightEvent.ChangeHeightUnit -> {
                state = state.copy(selectedHeightUnit = event.heightUnit)
            }

            is HeightEvent.ChangeHeight -> {
                state = state.copy(heightCm = event.centimeters)
            }

            is HeightEvent.SaveHeight -> {
                saveHeight(heightCm = state.heightCm, heightUnit = state.selectedHeightUnit)
            }
        }
    }

    private fun sendUiEvent(uiEvent: HeightUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun getHeightUnit() {
        viewModelScope.launch {
            val heightUnit = appSettingsManager.getHeightUnit()
            state = state.copy(selectedHeightUnit = heightUnit)
        }
    }

    private fun getHeight() {
        viewModelScope.launch {
            val user = userManager.getUser()
            state = state.copy(heightCm = user.heightCm)
        }
    }

    private fun saveHeight(heightCm: Int, heightUnit: HeightUnit) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = setHeightUseCase(heightCm = heightCm)) {
                is AppResponse.Success -> {
                    appSettingsManager.changeHeightUnit(heightUnit)
                    sendUiEvent(uiEvent = HeightUiEvent.HeightSaved)
                }

                is AppResponse.Failed -> {
                    handleException(exception = result.exception, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(exception: Exception, message: String?) {
        when (exception) {
            is InvalidHeightException -> {
                sendUiEvent(
                    uiEvent = HeightUiEvent.ShowSnackbar(
                        application.getString(R.string.error_invalid_height)
                    )
                )
            }

            else -> {
                sendUiEvent(
                    uiEvent = HeightUiEvent.ShowSnackbar(
                        message ?: application.getString(R.string.error_unknown)
                    )
                )
            }
        }
    }
}