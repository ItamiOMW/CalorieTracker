package com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.diary_feature.domain.exceptions.MealException
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.CreateConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.CreateMeal
import com.itami.calorie_tracker.diary_feature.domain.use_case.CreateMealUseCase
import com.itami.calorie_tracker.diary_feature.presentation.DiaryGraphScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMealViewModel @Inject constructor(
    private val createMealUseCase: CreateMealUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<NewMealUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(NewMealState())
        private set

    private var datetime: String = DateTimeUtil.getCurrentDateTimeString()

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(DiaryGraphScreens.ENCODED_DATETIME_ARG)
                ?.let { encodedDateTime ->
                    val datetime = Uri.decode(encodedDateTime)
                    this@NewMealViewModel.datetime = datetime
                } ?: throw Exception("Date argument was not passed.")
        }
    }

    fun onAction(action: NewMealAction) {
        when (action) {
            is NewMealAction.AddConsumedFoodRequest -> {
                addConsumedFood(action.consumedFood)
            }

            is NewMealAction.MealNameChange -> {
                state = state.copy(mealName = action.newValue)
            }

            is NewMealAction.SaveMealClick -> {
                createMeal(
                    name = state.mealName,
                    consumedFoods = state.consumedFoods,
                    datetime = datetime
                )
            }

            is NewMealAction.DeleteConsumedFood -> {
                deleteConsumedFood(action.index)
            }

            is NewMealAction.UpdateConsumedFood -> {
                editConsumedFood(action.index, action.weightGrams)
            }

            is NewMealAction.SelectConsumedFood -> {
                state = state.copy(selectedConsumedFoodIndex = action.index)
            }

            is NewMealAction.AddFoodIconClick -> {
                sendUiEvent(NewMealUiEvent.NavigateToSearchFood)
            }

            is NewMealAction.NavigateBackClick -> {
                state = state.copy(showExitDialog = true)
            }

            is NewMealAction.NavigateBackConfirmClick -> {
                state = state.copy(showExitDialog = false)
                sendUiEvent(NewMealUiEvent.NavigateBack)
            }

            is NewMealAction.NavigateBackDenyClick -> {
                state = state.copy(showExitDialog = false)
            }
        }
    }

    private fun sendUiEvent(uiEvent: NewMealUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun addConsumedFood(
        consumedFood: ConsumedFood,
    ) {
        val consumedFoods = state.consumedFoods + consumedFood
        state = state.copy(consumedFoods = consumedFoods)
    }

    private fun editConsumedFood(
        index: Int,
        weightGrams: Int,
    ) {
        val consumedFoods = state.consumedFoods.toMutableList().apply {
            val consumedFood = this[index]
            this[index] = consumedFood.copy(grams = weightGrams)
        }
        state = state.copy(consumedFoods = consumedFoods, selectedConsumedFoodIndex = null)
    }

    private fun deleteConsumedFood(
        index: Int,
    ) {
        val consumedFoods = state.consumedFoods.toMutableList().apply {
            removeAt(index)
        }
        state = state.copy(consumedFoods = consumedFoods)
    }

    private fun createMeal(
        name: String,
        consumedFoods: List<ConsumedFood>,
        datetime: String,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val createMeal = CreateMeal(
                name = name,
                createAt = datetime,
                consumedFoods = consumedFoods.map {
                    CreateConsumedFood(
                        foodId = it.food.id,
                        grams = it.grams
                    )
                }
            )
            when (val result = createMealUseCase(createMeal)) {
                is AppResponse.Success -> {
                    sendUiEvent(NewMealUiEvent.MealSaved)
                }

                is AppResponse.Failed -> {
                    handleException(appException = result.appException, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                val messageError = application.getString(R.string.error_network)
                sendUiEvent(NewMealUiEvent.ShowSnackbar(messageError))
            }

            is MealException.EmptyMealNameException -> {
                val messageError = application.getString(R.string.error_empty_meal_name)
                sendUiEvent(NewMealUiEvent.ShowSnackbar(messageError))
            }

            else -> {
                val messageError = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(NewMealUiEvent.ShowSnackbar(messageError))
            }
        }
    }
}