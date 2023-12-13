package com.itami.calorie_tracker.diary_feature.presentation.screens.meal

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.NetworkException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.diary_feature.domain.exceptions.EmptyMealNameException
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.CreateConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.UpdateMeal
import com.itami.calorie_tracker.diary_feature.domain.use_case.GetMealByIdUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.UpdateMealUseCase
import com.itami.calorie_tracker.diary_feature.presentation.DiaryGraphScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<MealUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(MealState())
        private set

    private var mealId: Int = Constants.UNKNOWN_ID

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(DiaryGraphScreens.MEAL_ID_ARG)?.let { mealId ->
                this@MealViewModel.mealId = mealId
                getMealById(mealId)
            } ?: throw Exception("Meal Id argument was not passed.")
        }
    }

    fun onEvent(event: MealEvent) {
        when (event) {
            is MealEvent.AddConsumedFood -> {
                addConsumedFood(event.consumedFood)
            }

            is MealEvent.MealNameChange -> {
                state = state.copy(mealName = event.newValue)
            }

            is MealEvent.SaveMeal -> {
                updateMeal(
                    mealId = mealId,
                    name = state.mealName,
                    consumedFoods = state.consumedFoods,
                )
            }

            is MealEvent.ShowExitDialog -> {
                state = state.copy(showExitDialog = event.show)
            }

            is MealEvent.DeleteConsumedFood -> {
                deleteConsumedFood(event.index)
            }

            is MealEvent.UpdateConsumedFood -> {
                updateConsumedFood(event.index, event.weightGrams)
            }

            is MealEvent.SelectConsumedFood -> {
                state = state.copy(selectedConsumedFoodIndex = event.index)
            }
        }
    }

    private fun sendUiEvent(uiEvent: MealUiEvent) {
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

    private fun updateConsumedFood(
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

    private fun updateMeal(
        mealId: Int,
        name: String,
        consumedFoods: List<ConsumedFood>,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val updateMeal = UpdateMeal(
                name = name,
                consumedFoods = consumedFoods.map { consumedFood ->
                    CreateConsumedFood(foodId = consumedFood.food.id, grams = consumedFood.grams)
                }
            )
            when (val result = updateMealUseCase(mealId = mealId, updateMeal = updateMeal)) {
                is AppResponse.Success -> {
                    sendUiEvent(MealUiEvent.MealSaved)
                }

                is AppResponse.Failed -> {
                    handleException(exception = result.exception, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun getMealById(mealId: Int) {
        viewModelScope.launch {
            getMealByIdUseCase(mealId = mealId)
                .collectLatest { meal ->
                    if (meal != null) {
                        state = state.copy(
                            mealName = meal.name,
                            consumedFoods = meal.consumedFoods,
                        )
                    }
                }
        }
    }

    private fun handleException(exception: Exception, message: String?) {
        when (exception) {
            is NetworkException -> {
                val messageError = application.getString(R.string.error_network)
                sendUiEvent(MealUiEvent.ShowSnackbar(messageError))
            }

            is EmptyMealNameException -> {
                val messageError = application.getString(R.string.error_empty_meal_name)
                sendUiEvent(MealUiEvent.ShowSnackbar(messageError))
            }

            else -> {
                val messageError = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(MealUiEvent.ShowSnackbar(messageError))
            }
        }
    }
}