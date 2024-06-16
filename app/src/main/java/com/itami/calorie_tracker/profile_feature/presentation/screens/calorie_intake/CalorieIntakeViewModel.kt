package com.itami.calorie_tracker.profile_feature.presentation.screens.calorie_intake

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal
import com.itami.calorie_tracker.core.domain.model.UpdateUser
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.CalculateNutrientsUseCase
import com.itami.calorie_tracker.core.domain.use_case.UpdateUserUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalorieIntakeViewModel @Inject constructor(
    private val userManager: UserManager,
    private val calculateNutrientsUseCase: CalculateNutrientsUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<CalorieIntakeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(CalorieIntakeState())
        private set

    init {
        getRecommendedNutrients()
    }

    fun onAction(action: CalorieIntakeAction) {
        when (action) {
            is CalorieIntakeAction.CaloriesFieldClick -> {
                state = state.copy(sheetContent = CalorieIntakeScreenSheetContent.CALORIES)
            }

            is CalorieIntakeAction.ProteinsFieldClick -> {
                state = state.copy(sheetContent = CalorieIntakeScreenSheetContent.PROTEINS)
            }

            is CalorieIntakeAction.FatsFieldClick -> {
                state = state.copy(sheetContent = CalorieIntakeScreenSheetContent.FATS)
            }

            is CalorieIntakeAction.CarbsFieldClick -> {
                state = state.copy(sheetContent = CalorieIntakeScreenSheetContent.CARBS)
            }

            is CalorieIntakeAction.WaterFieldClick -> {
                state = state.copy(sheetContent = CalorieIntakeScreenSheetContent.WATER)
            }

            is CalorieIntakeAction.ChangeCalories -> {
                state = state.copy(
                    currentNutrients = state.currentNutrients.copy(caloriesGoal = action.calories),
                    sheetContent = null
                )
            }

            is CalorieIntakeAction.ChangeProteins -> {
                state = state.copy(
                    currentNutrients = state.currentNutrients.copy(proteinsGoal = action.proteins),
                    sheetContent = null
                )
            }

            is CalorieIntakeAction.ChangeFats -> {
                state = state.copy(
                    currentNutrients = state.currentNutrients.copy(fatsGoal = action.fats),
                    sheetContent = null
                )
            }

            is CalorieIntakeAction.ChangeCarbs -> {
                state = state.copy(
                    currentNutrients = state.currentNutrients.copy(carbsGoal = action.carbs),
                    sheetContent = null
                )
            }

            is CalorieIntakeAction.ChangeWater -> {
                state = state.copy(
                    currentNutrients = state.currentNutrients.copy(waterMlGoal = action.waterMl),
                    sheetContent = null
                )
            }

            is CalorieIntakeAction.DismissBottomSheet -> {
                state = state.copy(sheetContent = null)
            }

            is CalorieIntakeAction.NavigateBackClick -> {
                sendUiEvent(CalorieIntakeUiEvent.NavigateBack)
            }

            is CalorieIntakeAction.SaveClick -> {
                saveCalorieIntake(
                    dailyNutrientsGoal = state.currentNutrients,
                    recommendedNutrientsGoal = state.recommendedNutrients,
                )
            }
        }
    }

    private fun saveCalorieIntake(
        dailyNutrientsGoal: DailyNutrientsGoal,
        recommendedNutrientsGoal: DailyNutrientsGoal,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            if (dailyNutrientsGoal == recommendedNutrientsGoal) {
                sendUiEvent(CalorieIntakeUiEvent.CalorieIntakeSaved)
                return@launch
            }

            val updateUser = UpdateUser(dailyNutrientsGoal = dailyNutrientsGoal)
            val result = updateUserUseCase(updateUser)

            when (val response = result.response) {
                is AppResponse.Success -> {
                    sendUiEvent(CalorieIntakeUiEvent.CalorieIntakeSaved)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun getRecommendedNutrients() {
        viewModelScope.launch {
            userManager.user.collect { user ->
                val recommendedNutrients = calculateNutrientsUseCase(
                    age = user.age,
                    heightCm = user.heightCm,
                    weightGrams = user.weightGrams,
                    weightGoal = user.weightGoal,
                    gender = user.gender,
                    lifestyle = user.lifestyle,
                )
                val currentNutrients = user.dailyNutrientsGoal
                state = state.copy(
                    recommendedNutrients = recommendedNutrients,
                    currentNutrients = currentNutrients
                )
            }
        }
    }

    private fun sendUiEvent(event: CalorieIntakeUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(CalorieIntakeUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(CalorieIntakeUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}