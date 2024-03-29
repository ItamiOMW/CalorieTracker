package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.diary_feature.domain.use_case.AddConsumedWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.GetConsumedWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.GetMealsUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.LoadMealsWithWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.RemoveConsumedWaterUseCase
import com.itami.calorie_tracker.diary_feature.presentation.mapper.toConsumedWaterUi
import com.itami.calorie_tracker.diary_feature.presentation.mapper.toMealUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getConsumedWaterUseCase: GetConsumedWaterUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val loadMealsWithWaterUseCase: LoadMealsWithWaterUseCase,
    private val addConsumedWaterUseCase: AddConsumedWaterUseCase,
    private val removeConsumedWaterUseCase: RemoveConsumedWaterUseCase,
    private val appSettingsManager: AppSettingsManager,
    private val userManager: UserManager,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<DiaryUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(DiaryState())
        private set

    private val dateString get() = state.date.toString()

    private var waterServingSize: Int = Constants.DEFAULT_WATER_SERVING_ML

    private var getMealsJob: Job? = null
    private var getConsumedWaterJob: Job? = null
    private var loadMealsWithWaterJob: Job? = null

    init {
        getUser()
        getWaterServingSize()
        getMeals(date = dateString)
        getConsumedWater(date = dateString)
        loadMealsWithWater(date = dateString)
    }

    fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.ChangeDate -> {
                state = state.copy(date = event.date)
                loadMealsWithWater(date = dateString)
                getMeals(date = dateString)
                getConsumedWater(date = dateString)
            }

            is DiaryEvent.AddConsumedWater -> {
                addConsumedWater(waterServingSize, dateString)
            }

            is DiaryEvent.RemoveConsumedWater -> {
                removeConsumedWater(waterServingSize, dateString)
            }

            is DiaryEvent.ShowDatePicker -> {
                state = state.copy(showDatePicker = event.show)
            }

            is DiaryEvent.Refresh -> {
                state = state.copy(isRefreshingMeals = true)
                loadMealsWithWater(date = dateString)
                state = state.copy(isRefreshingMeals = false)
            }
        }
    }

    private fun sendUiEvent(uiEvent: DiaryUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun removeConsumedWater(waterMl: Int, date: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = removeConsumedWaterUseCase(waterMl, date)) {
                is AppResponse.Success -> {
                    Unit
                }

                is AppResponse.Failed -> {
                    handleException(appException = result.appException, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun addConsumedWater(waterMl: Int, date: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = addConsumedWaterUseCase(waterMl, date)) {
                is AppResponse.Success -> {
                    Unit
                }

                is AppResponse.Failed -> {
                    handleException(appException = result.appException, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun getConsumedWater(date: String) {
        getConsumedWaterJob?.cancel()
        getConsumedWaterJob = viewModelScope.launch {
            getConsumedWaterUseCase(date).collectLatest { consumedWater ->
                state = state.copy(consumedWater = consumedWater?.toConsumedWaterUi())
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getMeals(date: String) {
        getMealsJob?.cancel()
        getMealsJob = viewModelScope.launch {
            getMealsUseCase(date).mapLatest { meals ->
                meals.map { it.toMealUi() }
            }.collectLatest { meals ->
                val proteins = meals.sumOf { it.proteins }
                val fats = meals.sumOf { it.fats }
                val carbs = meals.sumOf { it.carbs }
                val calories = meals.sumOf { it.calories }

                state = state.copy(
                    meals = meals,
                    consumedProteins = proteins,
                    consumedFats = fats,
                    consumedCarbs = carbs,
                    consumedCalories = calories,
                )
            }
        }
    }

    private fun loadMealsWithWater(date: String) {
        loadMealsWithWaterJob?.let { job ->
            state = state.copy(isRefreshingMeals = false, isLoadingMeals = false)
            job.cancel()
        }
        state = state.copy(isLoadingMeals = true)
        loadMealsWithWaterJob = viewModelScope.launch {
            when (val result = loadMealsWithWaterUseCase(date)) {
                is AppResponse.Success -> {
                    Unit
                }

                is AppResponse.Failed -> {
                    handleException(appException = result.appException, message = result.message)
                }
            }
            state = state.copy(isLoadingMeals = false)
        }
    }

    private fun getWaterServingSize() {
        viewModelScope.launch {
            appSettingsManager.waterServingSize.collectLatest { sizeMl ->
                this@DiaryViewModel.waterServingSize = sizeMl
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userManager.user.collect { user ->
                state = state.copy(user = user)
            }
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                val messageError = application.getString(R.string.error_network)
                state = state.copy(messageError = messageError)
                sendUiEvent(DiaryUiEvent.ShowSnackbar(messageError))
            }

            else -> {
                val messageError = message ?: application.getString(R.string.error_unknown)
                state = state.copy(messageError = messageError)
                sendUiEvent(DiaryUiEvent.ShowSnackbar(messageError))
            }
        }
    }
}