package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserInfoException
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.reports_feature.domain.use_case.AddWeightUseCase
import com.itami.calorie_tracker.reports_feature.domain.use_case.EditWeightUseCase
import com.itami.calorie_tracker.reports_feature.domain.use_case.GetWeightsUseCase
import com.itami.calorie_tracker.reports_feature.presentation.mapper.toWeightUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getWeightsUseCase: GetWeightsUseCase,
    private val addWeightUseCase: AddWeightUseCase,
    private val editWeightUseCase: EditWeightUseCase,
    private val application: Application,
    private val userManager: UserManager,
    private val appSettingsManager: AppSettingsManager,
) : ViewModel() {

    private val _uiEvent = Channel<ReportsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ReportsState())
        private set

    private var getWeightsJob: Job? = null

    init {
        getUser()
        getWeightUnit()
        getWeights()
    }

    fun onAction(action: ReportsAction) {
        when (action) {
            is ReportsAction.ReloadWeights -> {
                state = state.copy(errorMessage = null)
                getWeights()
            }

            is ReportsAction.ChangeWeightUnit -> {
                changeWeightUnit(action.weightUnit)
            }

            is ReportsAction.AddWeight -> {
                state = state.copy(showAddWeightDialog = false)
                addWeight(
                    weightGrams = action.weightGrams,
                    datetime = DateTimeUtil.getCurrentDateTimeString()
                )
            }

            is ReportsAction.EditWeight -> {
                state = state.copy(weightToEdit = null)
                editWeight(
                    weightId = action.weightId,
                    weightGrams = action.weightGrams
                )
            }

            is ReportsAction.ProfilePictureClick -> {
                sendUiEvent(ReportsUiEvent.NavigateToProfile)
            }

            is ReportsAction.AddWeightClick -> {
                state = state.copy(showAddWeightDialog = true)
            }

            is ReportsAction.DismissAddWeightDialog -> {
                state = state.copy(showAddWeightDialog = false)
            }

            is ReportsAction.WeightClick -> {
                state = state.copy(weightToEdit = action.weight)
            }

            is ReportsAction.DismissEditWeightDialog -> {
                state = state.copy(weightToEdit = null)
            }
        }
    }

    private fun editWeight(
        weightGrams: Int,
        weightId: Int,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response =
                editWeightUseCase(weightGrams = weightGrams, weightId = weightId)) {
                is AppResponse.Success -> {
                    val weightUi = response.data.toWeightUi()
                    val weights = state.weights.toMutableList().apply {
                        val indexOfWeightWithSameDate = indexOfFirst {
                            weightUi.datetime.toLocalDate() == it.datetime.toLocalDate()
                        }
                        removeAt(indexOfWeightWithSameDate)
                        add(indexOfWeightWithSameDate, weightUi)
                        sortBy { it.datetime }
                    }
                    state = state.copy(weights = weights)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun addWeight(
        weightGrams: Int,
        datetime: String,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = addWeightUseCase(weightGrams = weightGrams, datetime = datetime)) {
                is AppResponse.Success -> {
                    val weightUi = response.data.toWeightUi()
                    val weights = state.weights.toMutableList().apply {
                        val indexOfWeightWithSameDate = indexOfFirst {
                            weightUi.datetime.toLocalDate() == it.datetime.toLocalDate()
                        }
                        if (indexOfWeightWithSameDate != Constants.UNKNOWN_ID) {
                            removeAt(indexOfWeightWithSameDate)
                            add(indexOfWeightWithSameDate, weightUi)
                        } else {
                            add(weightUi)
                            sortBy { it.datetime }
                        }
                    }
                    state = state.copy(weights = weights)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun getWeights() {
        getWeightsJob?.cancel()
        state = state.copy(isLoadingWeights = true)
        getWeightsJob = viewModelScope.launch {
            when (val response = getWeightsUseCase()) {
                is AppResponse.Success -> {
                    val weightsUi = response.data.map { it.toWeightUi() }
                    state = state.copy(weights = weightsUi)
                }

                is AppResponse.Failed -> {
                    state = state.copy(
                        errorMessage = response.message
                            ?: application.getString(R.string.error_failed_to_load_weight_report)
                    )
                    handleException(
                        appException = response.appException,
                        message = response.message
                    )
                }
            }
            state = state.copy(isLoadingWeights = false)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userManager.user.collect { user ->
                state = state.copy(user = user)
            }
        }
    }

    private fun changeWeightUnit(weightUnit: WeightUnit) {
        viewModelScope.launch {
            appSettingsManager.changeWeightUnit(weightUnit = weightUnit)
        }
    }

    private fun getWeightUnit() {
        viewModelScope.launch {
            appSettingsManager.weightUnit.collect { weightUnit ->
                state = state.copy(weightUnit = weightUnit)
            }
        }
    }

    private fun sendUiEvent(uiEvent: ReportsUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                val messageError = application.getString(R.string.error_network)
                sendUiEvent(ReportsUiEvent.ShowSnackbar(messageError))
            }

            is UserInfoException.InvalidWeightException -> {
                val messageError = application.getString(R.string.error_invalid_weight)
                sendUiEvent(ReportsUiEvent.ShowSnackbar(messageError))
            }

            else -> {
                val messageError = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(ReportsUiEvent.ShowSnackbar(messageError))
            }
        }
    }

}