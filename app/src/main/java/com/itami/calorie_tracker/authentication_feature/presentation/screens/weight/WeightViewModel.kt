package com.itami.calorie_tracker.authentication_feature.presentation.screens.weight

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.UserInfoException
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.SetWeightUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val setWeightUseCase: SetWeightUseCase,
    private val userManager: UserManager,
    private val appSettingsManager: AppSettingsManager,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<WeightUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(WeightState())
        private set

    init {
        getWeightUnit()
        getWeight()
    }

    fun onAction(action: WeightAction) {
        when (action) {
            is WeightAction.NavigateNextClick -> {
                saveWeight(
                    weight = state.weight.toFloat(),
                    weightUnit = state.selectedWeightUnit
                )
            }

            is WeightAction.WeightValueChange -> {
                state = state.copy(weight = action.weight)
            }

            is WeightAction.ChangeWeightUnit -> {
                changeWeightUnit(
                    weight = state.weight,
                    fromWeightUnit = state.selectedWeightUnit,
                    toWeightUnit = action.weightUnit
                )
            }

            is WeightAction.NavigateBackClick -> {
                sendUiEvent(WeightUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(uiEvent: WeightUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun getWeightUnit() {
        viewModelScope.launch {
            val weightUnit = appSettingsManager.getWeightUnit()
            state = state.copy(selectedWeightUnit = weightUnit)
        }
    }

    private fun changeWeightUnit(
        weight: String,
        fromWeightUnit: WeightUnit,
        toWeightUnit: WeightUnit,
    ) {
        if (fromWeightUnit == toWeightUnit) {
            return
        }
        viewModelScope.launch {
            val newWeight = fromWeightUnit.convert(
                fromUnit = fromWeightUnit,
                toUnit = toWeightUnit,
                weight = weight.toFloat()
            )
            val weightString = String.format(Locale.US, "%.1f", newWeight)
            state = state.copy(weight = weightString, selectedWeightUnit = toWeightUnit)
        }
    }

    private fun getWeight() {
        viewModelScope.launch {
            val user = userManager.getUser()
            state = when (state.selectedWeightUnit) {
                WeightUnit.POUND -> {
                    val weightString =
                        String.format(
                            Locale.US,
                            "%.1f",
                            WeightUnit.POUND.gramsToPounds(user.weightGrams)
                        )
                    state.copy(weight = weightString)
                }

                WeightUnit.KILOGRAM -> {
                    val weightString = String.format(
                        Locale.US,
                        "%.1f",
                        WeightUnit.KILOGRAM.gramsToKilograms(user.weightGrams)
                    )
                    state.copy(weight = weightString)
                }
            }
        }
    }

    private fun saveWeight(weight: Float, weightUnit: WeightUnit) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = setWeightUseCase(weight = weight, unit = weightUnit)) {
                is AppResponse.Success -> {
                    appSettingsManager.changeWeightUnit(weightUnit)
                    sendUiEvent(uiEvent = WeightUiEvent.WeightSaved)
                }

                is AppResponse.Failed -> {
                    handleException(exception = result.appException, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(exception: Exception, message: String?) {
        when (exception) {
            is UserInfoException.InvalidWeightException -> {
                sendUiEvent(
                    uiEvent = WeightUiEvent.ShowSnackbar(
                        message = application.getString(R.string.error_invalid_weight)
                    )
                )
            }

            else -> {
                sendUiEvent(
                    uiEvent = WeightUiEvent.ShowSnackbar(
                        message = message ?: application.getString(R.string.error_unknown)
                    )
                )
            }
        }
    }
}